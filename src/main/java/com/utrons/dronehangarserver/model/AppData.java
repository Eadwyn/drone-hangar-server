package com.utrons.dronehangarserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utrons.dronehangarserver.comm.protocol.data.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AppData {
	private static AppData instance = new AppData();

	private FlightData flightData = new FlightData();
	private HangarData hangarData = new HangarData();
	private TextData textData = new TextData();
	private WayPointData wayPointData = new WayPointData();
	private WeatherData weatherData = new WeatherData();

	private AppData() {
	}

	public static AppData getInstance() {
		return instance;
	}

	//region setters
	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public void setFlightData(FlightData flightData) {
		this.flightData = flightData;
		this.log(this.flightData);
	}

	public void setHangarData(HangarData hangarData) {
		this.hangarData = hangarData;
		this.log(this.hangarData);
	}

	public void setTextData(TextData textData) {
		this.textData = textData;
		this.log(this.textData);
	}

	public void setWayPointData(WayPointData wayPointData) {
		this.wayPointData = wayPointData;
		this.log(this.wayPointData);
	}

	public void setWeatherData(WeatherData weatherData) {
		this.weatherData = weatherData;
		this.log(this.weatherData);
	}

	private void log(Object obj) {
		try {
			log.info("接收到数据:" + OBJECT_MAPPER.writeValueAsString(this.flightData));
		} catch (JsonProcessingException e) {
			log.error("JSON转换失败", e);
		}
	}
	//endregion
}
