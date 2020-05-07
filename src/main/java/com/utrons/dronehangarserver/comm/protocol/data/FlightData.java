package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class FlightData implements Serializable {
	private static final long serialVersionUID = -64443633630677420L;
	/** 包序列号[U16] */
	private int seq;
	/** 总航点数[U8] */
	private int totalWayPoint;
	/** 当前所在航点[U8] */
	private int currentPoint;
	/** 信号强度[U8] */
	private int rssi;
	/** 飞机姿态横滚[S16]，实际值=Roll/10，单位：度 */
	private double roll;
	/** 飞机姿态俯仰[S16]，实际值=Pitch/10，单位：度 */
	private double pitch;
	/** 飞机姿态航向[S16]，实际值=Yaw/10，单位：度 */
	private double yaw;
	/** 当前经度[S32], 实际值= Longitude/10000000，单位：度 */
	private double longitude;
	/** 当前纬度[S32]，实际值= Latitude/10000000，单位：度 */
	private double latitude;
	/** 起飞高度[S16]，实际值=Hight/10，单位：米 */
	private float hight;
	/** 飞行距离[U16]，实际值=Distance/10，单位：米 */
	private float distance;
	/** 东向速度[S16]，实际值= EastSpeed/10,单位：度 */
	private float eastSpeed;
	/** 北向速度[S16]，实际值= NorthSpeed/10,单位：度 */
	private float northSpeed;
	/** 垂直速度[S16]，实际值= UpSpeed /10,单位：度 */
	private float upSpeed;
	/** 卫星数[U8] */
	private int satellite;
	/** 定位模式[U8] */
	private int fixType;
	/** 电压[U16]，实际值= Voltage /10,单位：伏 */
	private float voltage;
	/** 电流[S16]，实际值= Current /10,单位：安 */
	private float current;
	/** 电压百分比[U8] */
	private int percentage;
	/** 云台航向[S16]，实际值= GimbalYaw /10,单位：度 */
	private float gimbalYaw;
	/** 云台俯仰[S16]，实际值= GimbalPitch /10,单位：度 */
	private float gimbalPitch;
	/** 相机焦距[U8] */
	private int cameraFoce;
	/** 相机状态[U8] */
	private int cameraStatus;
	/** 飞行状态[U16] */
	private int status;
	/** 飞行报警[U32] */
//	private long warn;

	//region 飞行状态
	/** RTK连接状态 */
	private boolean isRTKConnected;
	/** 飞行模式 */
	private int flightMode;
	/** 马达状态 */
	private boolean areMotorsOn = false;
	/** 航点就绪 */
	private boolean isWayPointReady = false;
	/** 充电状态 */
	private boolean isBeingCharged = false;
	//endregion

	//region 报警
	/** 响应的ACK命令字[U8] */
	private byte ackCommand;
	/** 响应的ACK执行结果[U8] */
	private int ackResult;
	/** 响应的ACK命令子序列号[U16] */
	private int ackSeq;
	//endregion

	public static FlightData parse(ProtocolRequest request) {
		byte[] data = request.getBuff();

		FlightData obj = new FlightData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setTotalWayPoint(NumericUtil.getUnSigned8(data[2]));
		obj.setCurrent(NumericUtil.getUnSigned8(data[3]));
		obj.setRssi(NumericUtil.getUnSigned8(data[4]));
		obj.setRoll(NumericUtil.getSigned16(new byte[]{data[5], data[6]}, true) / 10.0);
		obj.setPitch(NumericUtil.getSigned16(new byte[]{data[7], data[8]}, true) / 10.0);
		obj.setYaw(NumericUtil.getSigned16(new byte[]{data[9], data[10]}, true) / 10.0);
		obj.setLongitude(NumericUtil.getSigned32(new byte[]{data[11], data[12], data[13], data[14]}, true) / 10000000.0);
		obj.setLatitude(NumericUtil.getSigned32(new byte[]{data[15], data[16], data[17], data[18]}, true) / 10000000.0);
		obj.setHight(NumericUtil.getSigned16(new byte[]{data[19], data[20]}, true) / 10.0f);
		obj.setDistance(NumericUtil.getUnSigned16(new byte[]{data[21], data[22]}, true) / 10.0f);
		obj.setEastSpeed(NumericUtil.getSigned16(new byte[]{data[23], data[24]}, true) / 10.0f);
		obj.setNorthSpeed(NumericUtil.getSigned16(new byte[]{data[25], data[26]}, true) / 10.0f);
		obj.setUpSpeed(NumericUtil.getSigned16(new byte[]{data[27], data[28]}, true) / 10.0f);
		obj.setSatellite(NumericUtil.getUnSigned8(data[29]));
		obj.setFixType(NumericUtil.getUnSigned8(data[30]));
		obj.setVoltage(NumericUtil.getUnSigned16(new byte[]{data[31], data[32]}, true) / 10.0f);
		obj.setCurrent(NumericUtil.getSigned16(new byte[]{data[33], data[34]}, true) / 10.0f);
		obj.setPercentage(NumericUtil.getUnSigned8(data[35]));
		obj.setGimbalYaw(NumericUtil.getSigned16(new byte[]{data[36], data[37]}, true) / 10.0f);
		obj.setGimbalPitch(NumericUtil.getSigned16(new byte[]{data[38], data[39]}, true) / 10.0f);
		obj.setCameraFoce(NumericUtil.getUnSigned8(data[40]));
		obj.setCameraStatus(NumericUtil.getUnSigned8(data[41]));

		obj.setStatus(NumericUtil.getUnSigned16(new byte[]{data[42], data[43]}, true));

		obj.setAckCommand(data[4]);
		obj.setAckResult(data[45]);
		obj.setAckSeq(NumericUtil.getUnSigned16(new byte[]{data[46], data[47]}, true));

		return obj;
	}

	private void setFixType(int fixType) {
		switch (fixType) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				break;
			case 7:
				this.isRTKConnected = true;
				break;
		}
	}

	private void setStatus(int status) {
		byte[] statusBytes = NumericUtil.intToBytes(status, 2, true);
		byte low = statusBytes[0];
		byte high = statusBytes[1];

		this.flightMode = NumericUtil.getUnSigned8(low);

		int motors = (high >>> 0) & 0x01; // 马达状态 1 Bool 0-马达关，1-马达开
		int wayPointReady = (high >>> 1) & 0x01; // 点就绪 1 bool 0-航点未上传成功，1-航点已上传成功
		int beingCharged = (high >>> 2) & 0x01; // 充电状态 1 bool 0-用电模式，1-充电模式

		this.areMotorsOn = motors == 1 ? true : false;
		this.isWayPointReady = wayPointReady == 1 ? true : false;
		this.isBeingCharged = beingCharged == 1 ? true : false;
	}
}
