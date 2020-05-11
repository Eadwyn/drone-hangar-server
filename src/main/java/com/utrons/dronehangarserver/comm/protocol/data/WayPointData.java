package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class WayPointData implements Serializable {
	private static final long serialVersionUID = -4376365165862229636L;

	//region 属性
	/** 【U16】航点序号 0~n-1 */
	private int seq;
	/** 【U16】航点命令 */
	private int command;
	/** 【S32】航点经度, 实际值= Longitude /10000000，单位：度 */
	private double longitude;
	/** 【S32】航点纬度，实际值= Latitude/10000000，单位：度 */
	private double latitude;
	/** 【S16】航点高度，实际值=Height/10.0f，单位：米 */
	private float height;
	/** 【S16】云台方向，实际值= GimbalYaw /10.0f，单位：度 */
	private float gimbalYaw;
	/** 【S16】云台俯仰，实际值= GimbalPitch /10.0f，单位：度 */
	private float gimbalPitch;
	/** 【U8】相机模式，参考 RF_GIMBAL_CMD 命令 */
	private int cameraMode;
	/** 【U8】相机参数，填 0 */
	private int cameraPara;
	//endregion

	public static WayPointData parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		WayPointData obj = new WayPointData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setCommand(NumericUtil.getUnSigned16(new byte[]{data[2], data[3]}, true));
		obj.setLongitude(NumericUtil.getSigned32(new byte[]{data[4], data[5], data[6], data[7]}, true) / 10000000.0);
		obj.setLatitude(NumericUtil.getSigned32(new byte[]{data[8], data[9], data[10], data[11]}, true) / 10000000.0);
		obj.setHeight(NumericUtil.getUnSigned16(new byte[]{data[12], data[13]}, true) / 10.0f);
		obj.setGimbalYaw(NumericUtil.getUnSigned16(new byte[]{data[14], data[15]}, true) / 10.0f);
		obj.setGimbalPitch(NumericUtil.getUnSigned16(new byte[]{data[16], data[17]}, true) / 10.0f);
		obj.setCameraMode(NumericUtil.getUnSigned8(data[18]));
		obj.setCameraPara(NumericUtil.getUnSigned8(data[19]));
		return obj;
	}
}
