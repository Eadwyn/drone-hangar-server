package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class HangarData implements Serializable {

	private static final long serialVersionUID = -6427842655445731710L;
	/** 【U16】包序列号,发完一包累加 */
	private int seq;
	/** 【U8】舱盖，1-关闭，2-打开，3-正在关闭,4-正在打开 */
	private int cover;
	/** 【U8】回中器，1-夹紧，2-放松，3-运动过程 */
	private int folder;
	/** 【U8】起降台，1-降下，2-升起，3-降下过程，4-升起过程 */
	private int lifts;
	/** 【U8】内部灯，0-关闭，1-打开 */
	private boolean isInLampOn;
	/** 【U8】外部灯，0-关闭，1-打开 */
	private boolean isOutLampOn;
	/** 【U8】停机坪电源，0-关闭，1-打开 */
	private boolean isHangarPowerOn;
	/** 【U8】充电电源，0-关闭，1-打开 */
	private boolean isChargerPowerOn;
	/** 【U8】射频电源，0-关闭，1-打开 */
	private boolean isRfPowerOn;
	/** 【U8】摄像头电源，0-关闭，1-打开 */
	private boolean isCameraPowerOn;
	/** 【U8】遥控电源，0-关闭，1-打开 */
	private boolean isRemotePowerOn;
	/** 【U8】RTK 基站电源，0-关闭，1-打开 */
	private boolean isRtkPowerOn;
	/** 【U8】显示电源，0-关闭，1-打开 */
	private boolean isScreenPowerOn;
	/** 【U8】喊话器电源，0-关闭，1-打开 */
	private boolean isSpeakerPowerOn;
	/** 【U8】伺服电源，0-关闭，1-打开 */
	private boolean isDriverPowerOn;

	public static HangarData parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		HangarData obj = new HangarData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setCover(NumericUtil.getUnSigned8(data[2]));
		obj.setFolder(NumericUtil.getUnSigned8(data[3]));
		obj.setLifts(NumericUtil.getUnSigned8(data[4]));
		obj.setInLampOn(NumericUtil.getUnSigned8(data[5]) == 1 ? true : false);
		obj.setOutLampOn(NumericUtil.getUnSigned8(data[6]) == 1 ? true : false);
		obj.setHangarPowerOn(NumericUtil.getUnSigned8(data[7]) == 1 ? true : false);
		obj.setChargerPowerOn(NumericUtil.getUnSigned8(data[8]) == 1 ? true : false);
		obj.setRfPowerOn(NumericUtil.getUnSigned8(data[9]) == 1 ? true : false);
		obj.setCameraPowerOn(NumericUtil.getUnSigned8(data[10]) == 1 ? true : false);
		obj.setRemotePowerOn(NumericUtil.getUnSigned8(data[11]) == 1 ? true : false);
		obj.setRtkPowerOn(NumericUtil.getUnSigned8(data[12]) == 1 ? true : false);
		obj.setScreenPowerOn(NumericUtil.getUnSigned8(data[13]) == 1 ? true : false);
		obj.setSpeakerPowerOn(NumericUtil.getUnSigned8(data[14]) == 1 ? true : false);
		obj.setDriverPowerOn(NumericUtil.getUnSigned8(data[15]) == 1 ? true : false);
		return obj;
	}
}
