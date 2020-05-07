package com.utrons.dronehangarserver.thread;

import com.utrons.dronehangarserver.comm.TCPClientHelper;

public class CommReceiverThread extends UBaseThread {
	private static CommReceiverThread instance;

	private CommReceiverThread() {
	}

	public static CommReceiverThread getInstance() {
		if (null == instance) {
			synchronized (CommReceiverThread.class) {
				if (null == instance) {
					instance = new CommReceiverThread();
				}
			}
		}
		return instance;
	}

	@Override
	protected UBaseThread newInstance() {
		return new CommReceiverThread();
	}

	@Override
	protected void worker() {
		TCPClientHelper.getTcpClient().receive();
	}
}
