package com.utrons.dronehangarserver.comm.protocol.response;

import com.utrons.dronehangarserver.comm.protocol.model.ProtocolFrame;
import com.utrons.dronehangarserver.util.NumericUtil;

public class TcpResponse extends ProtocolFrame {

	/**
	 * @param pkgSeq      包序列号
	 * @param uintCommand 命令字
	 * @param pkgPayload  真正的数据域
	 * @return
	 */
	public static TcpResponse build(byte[] username, byte[] password, byte pkgSeq, int uintCommand, byte[] pkgPayload) {
		TcpResponse obj = new TcpResponse();
		obj.setPkgLength(pkgPayload.length > 0 ? (byte) pkgPayload.length : 0);
		obj.setUsername(username);
		obj.setPassword(password);
		obj.setPkgSeq(pkgSeq);
		obj.setUintCommand(uintCommand);

//		obj.setPkgSystemId(0x00);
		obj.setUintCommand(uintCommand);

		byte[] data = new byte[pkgPayload.length + 20];
		data[0] = START_FLAT_BIT;

		System.arraycopy(username, 0, data, 2, 6);
		System.arraycopy(password, 0, data, 8, 6);

		data[14] = pkgSeq;
		data[15] = 0x00;

		byte[] pkgCommand = NumericUtil.intToBytes(uintCommand, 2, true);
		data[16] = pkgCommand[0];
		data[17] = pkgCommand[1];

		if (null == pkgPayload) {
			data[1] = 0;
		} else {
			data[1] = (byte) (pkgPayload.length);
			System.arraycopy(pkgPayload, 0, data, 18, pkgPayload.length);
			obj.setUintChecksum(calChecksum(data, 1, data.length - 2));
			data[pkgPayload.length + 18] = obj.getPkgChecksum()[0];
			data[pkgPayload.length + 19] = obj.getPkgChecksum()[1];
		}

		obj.setBuff(data);
		obj.setCount(data.length);

		return obj;
	}
}
