package com.zephyr.web.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkConfig {

	@Autowired
	ZkConstant zkConst;

	@Bean(initMethod = "start")
	public CuratorFramework client() {
		return CuratorFrameworkFactory.newClient(zkConst.getConnectString(), zkConst.getSessionTimeoutMs(),
				zkConst.getConnectionTimeoutMs(), new RetryNTimes(zkConst.getRetryCount(), zkConst.getElapsedTimeMs()));
	}
}
