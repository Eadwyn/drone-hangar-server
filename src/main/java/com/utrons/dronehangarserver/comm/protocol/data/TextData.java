package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class TextData implements Serializable {

	/** 【U16】包序列号,发完一包累加 1 */
	private int seq;
	/** 【S8】在界面文本框内显示 */
	private int text;

	public static TextData parse(ProtocolRequest request) {
		byte[] data = request.getBuff();

		TextData obj = new TextData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setText(data[2]);
		return obj;
	}
}
