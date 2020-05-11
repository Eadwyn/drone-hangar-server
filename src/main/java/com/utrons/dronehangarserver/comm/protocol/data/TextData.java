package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class TextData implements Serializable {

	private static final long serialVersionUID = -469277690493399865L;
	/** 【U16】包序列号,发完一包累加 1 */
	private int seq;
	/** 【S8】在界面文本框内显示 */
	private String text;

	public static TextData parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		TextData obj = new TextData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));

		byte[] text = new byte[request.getPkgLength() - 22];
		System.arraycopy(data, 19, text, 0, text.length);
		obj.setText(new String(text));
		return obj;
	}
}
