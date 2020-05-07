package com.utrons.dronehangarserver;

import com.utrons.dronehangarserver.comm.TCPClientHelper;
import com.utrons.dronehangarserver.thread.CommReceivedHandlerThread;
import com.utrons.dronehangarserver.thread.CommReceiverThread;
import com.utrons.dronehangarserver.thread.CommSenderThread;

public class Test {
	public static void main(String[] args) {
		CommReceivedHandlerThread.getInstance().start();
		CommReceiverThread.getInstance().start();
		CommSenderThread.getInstance().start();
		TCPClientHelper.connect();
	}
}
