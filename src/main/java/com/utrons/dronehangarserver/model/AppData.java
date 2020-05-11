package com.utrons.dronehangarserver.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utrons.dronehangarserver.comm.protocol.data.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

@Slf4j
@Data
public class AppData {
	private static AppData instance = new AppData();

	private FlightData flightData = new FlightData();
	private HangarData hangarData = new HangarData();
	private WeatherData weatherData = new WeatherData();

	@Setter(AccessLevel.NONE)
	private Vector<TextData> textData = new Vector<>();
	private byte[] username = new byte[6];
	private byte[] password = new byte[6];

	//region 航线
	/** 航线数据状态：0-未请求；1-正在接收；2-接收完成； */
	@Setter(AccessLevel.NONE)
	private int wayPointDataStatus = 0;
	private int totalWayPoints = 0;
	@Setter(AccessLevel.NONE)
	private int wayPointCounter = 0;
	@Getter(AccessLevel.NONE)
	private WayPointData curWayPoint;
	@Setter(AccessLevel.NONE)
	private Vector<WayPointData> wayPointData = new Vector<>();
	//endregion

	private AppData() {
	}

	public static AppData getInstance() {
		return instance;
	}

	public void addTextData(TextData textData) {
		this.textData.add(textData);
		this.log(this.textData);
	}

	public void clearTextData() {
		this.textData.clear();
	}

	//region 航线
	public synchronized void addWayPointData(WayPointData wayPointData) {
		if (this.wayPointDataStatus != 1) {
			return;
		}

		if (this.wayPointCounter != (wayPointData.getSeq() - 1)) {
			return;
		}

		this.curWayPoint = curWayPoint;
		this.wayPointCounter++;
		if (wayPointCounter == this.totalWayPoints) {
			this.wayPointDataStatus = 2;
		} else {
			this.wayPointDataStatus = 1;
		}

		this.wayPointData.add(wayPointData);
		this.log(this.wayPointData);
		this.loge(this.wayPointData);
	}

	public synchronized void clearWayPointData() {
		this.wayPointData.clear();
		this.curWayPoint = null;
		this.wayPointCounter = 0;
		this.wayPointDataStatus = 0;
	}

	public synchronized void setTotalWayPoints(int total) {
		this.totalWayPoints = total;
		this.curWayPoint = null;
		this.wayPointCounter = 0;
		this.wayPointDataStatus = 1;
	}
	//endregion

	//region setters
	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	}

	public void setFlightData(FlightData flightData) {
		this.flightData = flightData;
		this.log(this.flightData);
	}

	public void setHangarData(HangarData hangarData) {
		this.hangarData = hangarData;
		this.log(this.hangarData);
	}

	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
		this.log(this.weatherData);
	}

	private void log(Object obj) {
		try {
			log.debug("接收到数据:" + OBJECT_MAPPER.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			log.error("JSON转换失败", e);
		}
	}
	//endregion

	private void loge(Object obj) {
		try {
			log.debug("接收到数据:" + OBJECT_MAPPER.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			log.error("JSON转换失败", e);
		}
	}
}
