package com.utrons.dronehangarserver.comm;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class CommData {
	/**
	 * 待发送
	 */
	private static LinkedBlockingDeque<byte[]> sendCache = new LinkedBlockingDeque<>(1000);
	/**
	 * 已接收
	 */
	private static LinkedBlockingDeque<byte[]> receivedCache = new LinkedBlockingDeque<>(1000);

	/**
	 * 发送数据
	 *
	 * @param data
	 */
	public static void send(byte[] data) {
		sendCache.addLast(data);
	}

	/**
	 * 获取待发送数据。当出错或超出等待时间（500ms）无返回数据时返回null。否则返回待发送的数据
	 *
	 * @return
	 */
	public static byte[] getSend() {
		try {
			return sendCache.pollFirst(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}

	/**
	 * 获取接收数据。当出错或超出等待时间（500ms）无返回数据时返回null。否则返回接收到的数据
	 *
	 * @return
	 */
	public static byte[] getReceived() {
		try {
			return receivedCache.pollFirst(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}

	/**
	 * 保存接收到的数据
	 *
	 * @param data
	 */
	public static void received(byte[] data) {
		receivedCache.addLast(data);
	}
}
