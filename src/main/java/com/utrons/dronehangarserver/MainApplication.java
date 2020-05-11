package com.utrons.dronehangarserver;

import com.utrons.dronehangarserver.comm.TCPClientHelper;
import com.utrons.dronehangarserver.thread.CommReceivedHandlerThread;
import com.utrons.dronehangarserver.thread.CommReceiverThread;
import com.utrons.dronehangarserver.thread.CommSenderThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
		init();
	}

	private static void init() {
		CommReceivedHandlerThread.getInstance().start();
		CommReceiverThread.getInstance().start();
		CommSenderThread.getInstance().start();
		TCPClientHelper.connect();
	}
}
