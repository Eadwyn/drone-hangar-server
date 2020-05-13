package com.utrons.dronehangarserver.comm.protocol.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Data
public class TextData implements Serializable {

	private static final long serialVersionUID = -469277690493399865L;
	/** 【U16】包序列号,发完一包累加 1 */
	private int seq;
	/** 【S8】在界面文本框内显示 */
	private String text;

	/** 接收到的时间 */
	@JsonFormat(pattern = "HH:mm:ss SSS", timezone = "GWT+8")
	private Date time;

	public static TextData parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		TextData obj = new TextData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));

		byte[] text = new byte[request.getPkgLength() - 22];
		System.arraycopy(data, 19, text, 0, text.length);
		try {
			obj.setText(new String(new String(text, "gb2312").getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		obj.setTime(new Date());
		return obj;
	}
}
