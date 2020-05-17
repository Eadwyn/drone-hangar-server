package com.utrons.dronehangarserver.thread;

import com.utrons.dronehangarserver.model.AppData;

public class HttpSessionCleannerThread extends UBaseThread {
	private static HttpSessionCleannerThread instance;

	private HttpSessionCleannerThread() {
	}

	public static HttpSessionCleannerThread getInstance() {
		if (null == instance) {
			synchronized (HttpSessionCleannerThread.class) {
				if (null == instance) {
					instance = new HttpSessionCleannerThread();
				}
			}
		}
		return instance;
	}

	@Override
	protected UBaseThread newInstance() {
		return new HttpSessionCleannerThread();
	}

	@Override
	protected void worker() {
		AppData.getInstance().clearClients();
	}
}
