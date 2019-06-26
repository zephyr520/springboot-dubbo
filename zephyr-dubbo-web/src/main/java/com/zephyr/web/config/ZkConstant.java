package com.zephyr.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "curator")
public class ZkConstant {

	private int retryCount;
	private int elapsedTimeMs;
	private String connectString;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getElapsedTimeMs() {
		return elapsedTimeMs;
	}

	public void setElapsedTimeMs(int elapsedTimeMs) {
		this.elapsedTimeMs = elapsedTimeMs;
	}

	public String getConnectString() {
		return connectString;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getConnectionTimeoutMs() {
		return connectionTimeoutMs;
	}

	public void setConnectionTimeoutMs(int connectionTimeoutMs) {
		this.connectionTimeoutMs = connectionTimeoutMs;
	}

	@Override
	public String toString() {
		return "ZkConstant [retryCount=" + retryCount + ", elapsedTimeMs=" + elapsedTimeMs + ", connectString="
				+ connectString + ", sessionTimeoutMs=" + sessionTimeoutMs + ", connectionTimeoutMs="
				+ connectionTimeoutMs + "]";
	}
}
