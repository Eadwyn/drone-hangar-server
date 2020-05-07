package com.utrons.dronehangarserver.comm;

import com.utrons.dronehangarserver.comm.protocol.ProtocolParser;
import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommDataParser {

	/** 数据包缓存 */
	private static ProtocolRequest request = new ProtocolRequest();

	/**
	 * 从接收到的数据中提取有效包，并将有效包缓存到rfData中（一次接收到的数据可能包含多个数据包，并且一个数据包可能被分拆到两次接收中）
	 *
	 * @param data 接收到数据包
	 * @param len  有效包长度
	 */
	public static synchronized void handleProtocolData(byte[] data, int len) {
		for (int i = 0; i < len; i++) {
			parseRFData(data[i]);
		}
	}

	/**
	 * 以状态机机制对比每一字节
	 *
	 * @param data
	 */
	private static void parseRFData(byte data) {
		switch (request.getState()) {
			case 0:
				if (data == ProtocolRequest.START_FLAT_BIT) { // 起始位
					request.setCount(1);
					request.getBuff()[request.getCount() - 1] = data;
					request.setState(1);
				}
				break;

			case 1: // 包长度
				request.setPkgLength(data);
				request.setCount(2);
				request.getBuff()[request.getCount() - 1] = data;
				request.setState(2);
				break;

			case 2: // 用户名
				request.setCount(request.getCount() + 1);
				request.getBuff()[request.getCount() - 1] = data;
				if (request.getCount() >= 8) {
					request.setState(3);
				}
				break;

			case 3: // 密码
				request.setCount(request.getCount() + 1);
				request.getBuff()[request.getCount() - 1] = data;
				if (request.getCount() >= 14) {
					request.setState(4);
				}
				break;

			case 4: // 包序号
				request.setPkgSeq(data);
				request.setCount(15);
				request.getBuff()[request.getCount() - 1] = data;
				request.setState(5);
				break;

			case 5: // 系统id
				request.setPkgSystemId(data);
				request.setCount(16);
				request.getBuff()[request.getCount() - 1] = data;
				request.setState(6);
				break;

			case 6: // 命令字（低字节）
				request.setCount(17);
				request.getBuff()[request.getCount() - 1] = data;
				request.setState(7);
				break;

			case 7: // 命令字（高字节）
				request.setCount(18);
				request.getBuff()[request.getCount() - 1] = data;
				request.setPkgCommand(new byte[]{request.getBuff()[3], request.getBuff()[4]});
				if (request.getUintLength() > 0) { // 数据包长度大于0
					request.setState(8);
				} else { // 无数据包
					request.setState(9);
				}
				break;

			case 8: // 数据域
				request.setCount(request.getCount() + 1);
				request.getBuff()[request.getCount() - 1] = data;
				if (request.getCount() >= request.getUintLength() + 18) {
					request.setState(9);
				}
				break;

			case 9: // 校验码（低字节）
				request.setCount(request.getCount() + 1);
				request.getBuff()[request.getCount() - 1] = data;
				request.setState(10);
				break;

			case 10: // 校验码（高字节）
				request.setCount(request.getCount() + 1);
				request.getBuff()[request.getCount() - 1] = data;

				request.setPkgChecksum(new byte[]{request.getBuff()[request.getUintLength() + 18], request.getBuff()[request.getUintLength() + 19]});
				int checksum = ProtocolRequest.calChecksum(request.getBuff(), request.getUintLength() + 18);
				if (request.getUintChecksum() == checksum) {
					if (request.getUintLength() > 0) {
						byte[] payload = new byte[request.getUintLength()];
						System.arraycopy(request.getBuff(), 18, payload, 0, request.getUintLength());
						request.setPkgPayload(payload);
					}
					ProtocolParser.parse(request);
					log.debug("有效数据包 " + NumericUtil.bytesToHexStringWithBlank(request.getBuff()));
				} else {
					log.warn("无效数据包【校验失败】 " + NumericUtil.bytesToHexStringWithBlank(request.getBuff()));
				}
				request.setState(0);
				break;
		}
	}

	public static void main(String[] args) {
		byte[] data = new byte[]{(byte) 0xB2, (byte) 0x10, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE2, (byte) 0x00, (byte) 0x11, (byte) 0x08, (byte) 0xF3, (byte) 0xA4, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x15, (byte) 0x06, (byte) 0xB2, (byte) 0x17, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE3, (byte) 0x00, (byte) 0x12, (byte) 0x08, (byte) 0x0A, (byte) 0xCC, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x00, (byte) 0x82, (byte) 0x0A, (byte) 0x0F, (byte) 0x01, (byte) 0x53, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x5C, (byte) 0x06, (byte) 0xB2, (byte) 0x30, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE4, (byte) 0x00, (byte) 0x10, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x98, (byte) 0x04, (byte) 0xB2, (byte) 0x10, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE5, (byte) 0x00, (byte) 0x11, (byte) 0x08, (byte) 0xF3, (byte) 0xA4, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18, (byte) 0x06, (byte) 0xB2, (byte) 0x17, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE6, (byte) 0x00, (byte) 0x12, (byte) 0x08, (byte) 0x0A, (byte) 0xCC, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x00, (byte) 0x82, (byte) 0x0A, (byte) 0x0F, (byte) 0x01, (byte) 0x53, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x5F, (byte) 0x06, (byte) 0xB2, (byte) 0x30, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE7, (byte) 0x00, (byte) 0x10, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9B, (byte) 0x04, (byte) 0xB2, (byte) 0x10, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE8, (byte) 0x00, (byte) 0x11, (byte) 0x08, (byte) 0xF3, (byte) 0xA4, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1B, (byte) 0x06, (byte) 0xB2, (byte) 0x17, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x61, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0x31, (byte) 0xE9, (byte) 0x00, (byte) 0x12, (byte) 0x08, (byte) 0x0A, (byte) 0xCC, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x00, (byte) 0x82, (byte) 0x0A, (byte) 0x0F, (byte) 0x01, (byte) 0x53, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x62, (byte) 0x06};

		int splitLen = 0;
		int len = data.length;
		handleProtocolData(data, len - splitLen);

//		byte[] bytes = new byte[splitLen];
//		System.arraycopy(data, len - splitLen, bytes, 0, splitLen);
//		handleRFData(bytes, splitLen);
	}
}
