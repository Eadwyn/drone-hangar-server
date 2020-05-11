package com.utrons.dronehangarserver.model;

import com.utrons.dronehangarserver.comm.protocol.data.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class GetAllDataResponse implements Serializable {
	private static final long serialVersionUID = -7113051933173438704L;
	private FlightData flightData;
	private HangarData hangarData;
	private WeatherData weatherData;
	private List<WayPointData> wayPointData;
	private List<TextData> textData;
}
