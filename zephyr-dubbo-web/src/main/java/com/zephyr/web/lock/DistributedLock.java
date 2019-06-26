package com.zephyr.web.lock;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributedLock implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);
	
	private final static String ROOT_PATH_LOCK = "zephyr-lock";
	private CountDownLatch countDown = new CountDownLatch(1);
	@Autowired
	private CuratorFramework client;
	
	/**
	 * @desc 获取分布式锁
	 * @param nodePath
	 */
	public void acquireLock(String path) {
		String nodePath = "/" + ROOT_PATH_LOCK + "/" + path;
		while(true) {
			try {
				client.create().creatingParentsIfNeeded()
					.withMode(CreateMode.EPHEMERAL)
					.withACL(Ids.OPEN_ACL_UNSAFE)
					.forPath(nodePath);
				LOGGER.info("成功获取分布式锁:{}", nodePath);
				break;
			} catch (Exception e) {
				LOGGER.info("获取分布式锁失败:{}", nodePath);
				LOGGER.info("重试获取分布式锁......");
				try {
					// 如果没有获取到锁，需要重新设置同步资源
					if (countDown.getCount() <= 0) {
						countDown = new CountDownLatch(1);
					}
					// 阻塞线程
					countDown.await();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @desc 释放分布式锁
	 * @param path
	 * @return
	 */
	public boolean releaseLock(String path) {
		try {
			String nodePath = "/" + ROOT_PATH_LOCK + "/" + path;
			if (client.checkExists().forPath(nodePath) != null) {
				client.delete().forPath(nodePath);
			}
			LOGGER.info("分布式锁释放成功...");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("分布式锁释放失败...");
			return false;
		}
		return true;
	}

	/**
	 * 创建父节点
	 */
	@Override
	public void afterPropertiesSet() {
		client = client.usingNamespace("lock-namespace");
		String nodePath = "/" + ROOT_PATH_LOCK;
		try {
			if (client.checkExists().forPath(nodePath) == null) {
				client.create().creatingParentsIfNeeded()
						.withMode(CreateMode.PERSISTENT)
						.withACL(Ids.OPEN_ACL_UNSAFE)
						.forPath(nodePath);
			}
			addWatcherToLock(nodePath);
			LOGGER.info("{} watcher事件创建成功", ROOT_PATH_LOCK);
		}catch(Exception e) {
			LOGGER.error("连接zookeeper失败,日志 >> {}", e.getMessage(), e);
		}
	}

	/**
	 * 添加watcher事件监听
	 * @param path
	 */
	private void addWatcherToLock(String path) throws Exception {
		final PathChildrenCache childCache = new PathChildrenCache(client, path, false);
		childCache.start(StartMode.POST_INITIALIZED_EVENT);
		childCache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
					String oldPath = event.getData().getPath();
					LOGGER.info("上一个会话已经释放锁或该会话已经断开，节点路径为：{}", oldPath);
					if (oldPath.contains(path)) {
						//释放计数器，让当前的请求获取锁
						LOGGER.info("释放计数器，让当前请求获取锁");
						countDown.countDown();
					}
				}
			}
		});
	}

}
