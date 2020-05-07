package com.utrons.dronehangarserver.comm.protocol;

import com.utrons.dronehangarserver.comm.protocol.data.*;
import com.utrons.dronehangarserver.comm.protocol.model.ProtocolCommand;
import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.model.AppData;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtocolParser {
	//region 解析
	public static void parse(ProtocolRequest request) {
		int command = NumericUtil.getUnSigned16(request.getPkgCommand(), true);
		try {
			switch (command) {
				case ProtocolCommand.WEB_HEART_CMD:
					handle_WEB_HEART_CMD(request);
					break;
				case ProtocolCommand.WEB_TAKEOFF_CMD:
					handle_WEB_TAKEOFF_CMD(request);
					break;
				case ProtocolCommand.WEB_LANDING_CMD:
					handle_WEB_LANDING_CMD(request);
					break;
				case ProtocolCommand.WEB_RETURN_CMD:
					handle_WEB_RETURN_CMD(request);
					break;
				case ProtocolCommand.WEB_PAUSE_CMD:
					handle_WEB_PAUSE_CMD(request);
					break;
				case ProtocolCommand.WEB_RESUME_CMD:
					handle_WEB_RESUME_CMD(request);
					break;
				case ProtocolCommand.WEB_AUTO_CMD:
					handle_WEB_AUTO_CMD(request);
					break;
				case ProtocolCommand.WEB_WAYLINE_CMD:
					handle_WEB_WAYLINE_CMD(request);
					break;
				case ProtocolCommand.WEB_FLIGHT_CMD:
					handle_WEB_FLIGHT_CMD(request);
					break;
				case ProtocolCommand.WEB_GIMBAL_CMD:
					handle_WEB_GIMBAL_CMD(request);
					break;
				case ProtocolCommand.WEB_HANGAR_START:
					handle_WEB_HANGAR_START(request);
					break;
				case ProtocolCommand.WEB_HANGAR_CHARGER:
					handle_WEB_HANGAR_CHARGER(request);
					break;
				case ProtocolCommand.WEB_HANGAR_SLEEP:
					handle_WEB_HANGAR_SLEEP(request);
					break;
				case ProtocolCommand.WEB_HANGAR_STOP:
					handle_WEB_HANGAR_STOP(request);
					break;
				case ProtocolCommand.WEB_TEXT_DATA:
					handle_WEB_TEXT_DATA(request);
					break;
				case ProtocolCommand.WEB_FLIGHT_DATA:
					handle_WEB_FLIGHT_DATA(request);
					break;
				case ProtocolCommand.WEB_HANGAR_DATA:
					handle_WEB_HANGAR_DATA(request);
					break;
				case ProtocolCommand.WEB_WEATHER_DATA:
					handle_WEB_WEATHER_DATA(request);
					break;
				case ProtocolCommand.WEB_WAYPOINT_DATA:
					handle_WEB_WAYPOINT_DATA(request);
					break;
			}
		} catch (Exception e) {
			log.error("parse error: ", e);
		}
	}
	//endregion

	//region 具体命令处理
	//region 控制命令
	private static void handle_WEB_TAKEOFF_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_LANDING_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_RETURN_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_PAUSE_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_RESUME_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_AUTO_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_WAYLINE_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_FLIGHT_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_GIMBAL_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_HANGAR_START(ProtocolRequest request) {
	}

	private static void handle_WEB_HANGAR_CHARGER(ProtocolRequest request) {
	}

	private static void handle_WEB_HANGAR_SLEEP(ProtocolRequest request) {
	}

	private static void handle_WEB_HANGAR_STOP(ProtocolRequest request) {
	}
	//endregion

	//region 主动上传
	private static void handle_WEB_HEART_CMD(ProtocolRequest request) {
	}

	private static void handle_WEB_TEXT_DATA(ProtocolRequest request) {
		AppData.getInstance().setTextData(TextData.parse(request));
	}

	private static void handle_WEB_FLIGHT_DATA(ProtocolRequest request) {
		AppData.getInstance().setFlightData(FlightData.parse(request));
	}

	private static void handle_WEB_HANGAR_DATA(ProtocolRequest request) {
		AppData.getInstance().setHangarData(HangarData.parse(request));
	}

	private static void handle_WEB_WEATHER_DATA(ProtocolRequest request) {
		AppData.getInstance().setWeatherData(WeatherData.parse(request));
	}

	private static void handle_WEB_WAYPOINT_DATA(ProtocolRequest request) {
		AppData.getInstance().setWayPointData(WayPointData.parse(request));
	}
	//endregion
	//endregion
}
