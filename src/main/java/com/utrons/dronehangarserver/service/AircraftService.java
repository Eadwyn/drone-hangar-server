package com.utrons.dronehangarserver.service;

import com.utrons.dronehangarserver.comm.CommData;
import com.utrons.dronehangarserver.comm.protocol.model.ProtocolCommand;
import com.utrons.dronehangarserver.comm.protocol.response.TcpResponse;
import com.utrons.dronehangarserver.model.AppData;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class AircraftService {
	/** 发送包序号 */
	private static byte sendSeqCounter = 0;
	private static byte takeOffSubSeq = 0;
	private static byte landingSubSeq = 0;
	private static byte returnHomeSubSeq = 0;
	private static byte pauseSubSeq = 0;
	private static byte resumeSubSeq = 0;
	private static byte autoSubSeq = 0;
	private static byte chargerSubSeq = 0;
	private static byte hangarStartSubSeq = 0;
	private static byte hangerStopSubSeq = 0;
	private static byte hangarSleepSubSeq = 0;
	private static byte loadWayPointSubSeq = 0;
	private static byte readWayPointSubSeq = 0;

	//region 起飞
	public void takeOff() {
		this.send(ProtocolCommand.WEB_TAKEOFF_CMD, takeOffSubSeq);
	}
	//endregion

	//region 降落
	public void landing() {
		this.send(ProtocolCommand.WEB_LANDING_CMD, landingSubSeq);
	}
	//endregion

	//region 返航
	public void returnHome() {
		this.send(ProtocolCommand.WEB_RETURN_CMD, returnHomeSubSeq);
	}
	//endregion

	//region 暂停航线
	public void pause() {
		this.send(ProtocolCommand.WEB_PAUSE_CMD, pauseSubSeq);
	}
	//endregion

	//region 恢复航线
	public void resume() {
		this.send(ProtocolCommand.WEB_RESUME_CMD, resumeSubSeq);
	}
	//endregion

	//region 自动航线
	public void auto() {
		this.send(ProtocolCommand.WEB_AUTO_CMD, autoSubSeq);
	}
	//endregion

	//region 自动充电
	public void charger() {
		this.send(ProtocolCommand.WEB_HANGAR_CHARGER, chargerSubSeq);
	}
	//endregion

	//region 准备起飞
	public void hangarStart() {
		this.send(ProtocolCommand.WEB_HANGAR_START, hangarStartSubSeq);
	}
	//endregion

	//region 停止流程
	public void hangerStop() {
		this.send(ProtocolCommand.WEB_HANGAR_STOP, hangerStopSubSeq);
	}
	//endregion

	//region 休眠流程
	public void hangarSleep() {
		this.send(ProtocolCommand.WEB_HANGAR_SLEEP, hangarSleepSubSeq);
	}
	//endregion

	//region 加载航线
	public void loadWayPoint(int code) {
		if (-1 == code) {
			AppData.getInstance().clearWayPointData();
			return;
		}

		byte[] username = AppData.getInstance().getUsername();
		byte[] password = AppData.getInstance().getPassword();

		byte[] payload = new byte[4];
		byte[] subSeqBytes = NumericUtil.intToBytes(NumericUtil.getUnSigned8(loadWayPointSubSeq), 2, true);
		payload[0] = subSeqBytes[0];
		payload[1] = subSeqBytes[1];

		byte[] wayPointBytes = NumericUtil.intToBytes(code, 2, true);
		payload[2] = wayPointBytes[0];
		payload[3] = wayPointBytes[1];

		TcpResponse response = TcpResponse.build(username, password, sendSeqCounter, ProtocolCommand.WEB_WAYLINE_CMD, payload);

		AppData.getInstance().clearWayPointData();
		CommData.send(response.getBuff()); // 加入发送队列

		System.out.println("加载航线： " + NumericUtil.bytesToHexStringWithBlank(response.getBuff()));
		sendSeqCounter++;
		loadWayPointSubSeq++;
	}
	//endregion

	//region 读取航点
	private static ReadWayPointThread readWayPointThread;

	public static void startReadWayPoint(int total) {
		if (null != readWayPointThread) {
			readWayPointThread.stopThread();
		}
		readWayPointThread = new ReadWayPointThread(total);
		readWayPointThread.start();
	}

	@Data
	private static class ReadWayPointThread extends Thread {
		private boolean isFinish = false;
		private int total;
		private int current = 0;

		public ReadWayPointThread(int total) {
			this.total = total;
		}

		public void stopThread() {
			this.isFinish = true;
		}

		private void readWayPoint(int code) {
			byte[] username = AppData.getInstance().getUsername();
			byte[] password = AppData.getInstance().getPassword();

			byte[] payload = new byte[4];
			byte[] subSeqBytes = NumericUtil.intToBytes(NumericUtil.getUnSigned8(readWayPointSubSeq), 2, true);
			payload[0] = subSeqBytes[0];
			payload[1] = subSeqBytes[1];

			byte[] wayPointBytes = NumericUtil.intToBytes(code, 2, true);
			payload[2] = wayPointBytes[0];
			payload[3] = wayPointBytes[1];

			TcpResponse response = TcpResponse.build(username, password, sendSeqCounter, ProtocolCommand.WEB_WAYPOINT_DATA, payload);

			AppData.getInstance().clearWayPointData();
			CommData.send(response.getBuff()); // 加入发送队列
			System.out.println("读取航点： " + NumericUtil.bytesToHexStringWithBlank(response.getBuff()));
			sendSeqCounter++;
			readWayPointSubSeq++;
		}

		@Override
		public void run() {
			this.isFinish = false;
			while (!this.isFinish && this.current < this.total) {
				readWayPoint(this.current);
				if (this.current > AppData.getInstance().getWayPointCounter()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// ignore
					}
				} else {
					this.current++;
				}
			}
		}
	}
	//endregion

	private void send(int cmd, byte subSeq) {
		byte[] username = AppData.getInstance().getUsername();
		byte[] password = AppData.getInstance().getPassword();

		byte[] payload = new byte[4];
		byte[] subSeqBytes = NumericUtil.intToBytes(NumericUtil.getUnSigned8(subSeq), 2, true);
		payload[0] = subSeqBytes[0];
		payload[1] = subSeqBytes[1];
		payload[2] = 0x00;
		payload[3] = 0x00;

		TcpResponse response = TcpResponse.build(username, password, sendSeqCounter, cmd, payload);
		CommData.send(response.getBuff()); // 加入发送队列
		sendSeqCounter++;
		subSeq++;
	}
}
