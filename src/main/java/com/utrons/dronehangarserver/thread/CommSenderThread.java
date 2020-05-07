package com.utrons.dronehangarserver.thread;

import com.utrons.dronehangarserver.comm.CommData;
import com.utrons.dronehangarserver.comm.TCPClientHelper;

public class CommSenderThread extends UBaseThread {
	private static CommSenderThread instance;

	private CommSenderThread() {
	}

	public static CommSenderThread getInstance() {
		if (null == instance) {
			synchronized (CommSenderThread.class) {
				if (null == instance) {
					instance = new CommSenderThread();
				}
			}
		}
		return instance;
	}

	@Override
	protected UBaseThread newInstance() {
		return new CommSenderThread();
	}

	@Override
	protected void worker() {
		byte[] data = CommData.getSend();
		if (null != data) {
			TCPClientHelper.getTcpClient().send(data);
		}
	}
}
