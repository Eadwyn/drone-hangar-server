package com.utrons.dronehangarserver.comm.client;

import com.utrons.dronehangarserver.comm.CommData;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class TCPClient {

	/** 发送缓冲区大小 */
	public static final int SEND_BUFF_SIZE = 1024;

	/** 接收缓冲区大小 */
	public static final int READ_BUFF_SIZE = 1024;


	/** 与服务器间的连接通道 */
	private SocketChannel socketChannel;

	/** 服务器地址 */
	private InetSocketAddress serverAddress;

	/**
	 * 连接到服务器，并同时开启读取线程、发送线程
	 *
	 * @param serverAddress 服务器地址
	 */
	public void connect(InetSocketAddress serverAddress) {
		this.serverAddress = serverAddress;
		connect();
	}

	/**
	 * 连接到服务器，并同时开启读取线程、发送线程
	 */
	private void connect() {
		try {
			socketChannel = SocketChannel.open();// 打开连接通道
			log.info("正在连接到服务器 - " + this.serverAddress.getAddress().getHostAddress() + ":" + this.serverAddress.getPort());
			boolean b = socketChannel.connect(this.serverAddress);// 连接到服务器
			if (socketChannel.isConnectionPending()) {
				socketChannel.finishConnect();
			}
			if (!b || !socketChannel.isOpen() || !socketChannel.isConnected()) {
				log.error("连接服务器失败：未能打开连接");
				closeSocketChannel();
				return;
			}
			log.info("成功连接到服务器 - " + this.serverAddress.getAddress().getHostAddress() + ":" + this.serverAddress.getPort());
			socketChannel.configureBlocking(false);// 设置通道为非阻塞
		} catch (IOException e) {
			log.error("连接服务器失败：", e);
			closeSocketChannel();
		}
	}

	//region 发送
	/** 发送缓冲区 */
	private ByteBuffer sendBuffer = ByteBuffer.allocate(SEND_BUFF_SIZE);

	/**
	 * 向服务器发送信息。
	 *
	 * @param bytes 要发送信息的字节表示
	 * @throws IllegalArgumentException 当发生的信息dataBuff为null时，抛出此异常
	 */
	public void send(byte[] bytes) {
		Assert.isTrue(null != bytes, "发送数据不能为null");

		try {
			if (!this.checkConnection()) { // 连接未打开或已断开
				return;
			}

			sendBuffer.clear();// 清空发送缓冲区
			sendBuffer = ByteBuffer.wrap(bytes);// 将 buff 数组包装到缓冲区中
			socketChannel.write(sendBuffer);// 向服务器写命令
			log.debug(String.format("发送数据成功：%s", NumericUtil.bytesToHexStringWithBlank(bytes)));
		} catch (Exception ex) {
			closeSocketChannel();
			log.error(String.format("发送数据失败：%s", NumericUtil.bytesToHexStringWithBlank(bytes)), ex);
		}
	}
	//endregion

	//region 接收
	/** 接收缓冲区 */
	private ByteBuffer receiveBuffer = ByteBuffer.allocate(READ_BUFF_SIZE);

	public void receive() {
		try {
			if (!this.checkConnection()) { // 连接未打开或已断开
				return;
			}

			int len = -1;
			socketChannel.configureBlocking(true); // 设置接收为阻塞状态
			receiveBuffer.clear();// 清空缓冲区
			byte[] dataBuff = null; // 接收数据的字节表示
			while ((len = socketChannel.read(receiveBuffer)) > 0) {
				receiveBuffer.flip();
				receiveBuffer.flip();
				dataBuff = new byte[len];
				System.arraycopy(receiveBuffer.array(), 0, dataBuff, 0, len);
				CommData.received(dataBuff);
				log.debug(String.format("接收数据成功：%s", NumericUtil.bytesToHexStringWithBlank(dataBuff)));
			}
		} catch (Exception ex) {
			closeSocketChannel();
			log.error("接收数据 失败：", ex);
			return;
		}
	}
	//endregion

	/**
	 * 关闭连接通道
	 */
	private void closeSocketChannel() {
		if (this.socketChannel != null) {
			try {
				this.socketChannel.close();
			} catch (IOException e) {
				log.error("关闭与服务器间的连接时发生错误：", e);
			}
		}
	}

	private boolean checkConnection() {
		if (null != socketChannel && socketChannel.isOpen() && socketChannel.isConnected()) {
			return true;
		}

		log.error("与服务器间未建立连接，或者连接已经断开");
		return false;
	}
}
