package com.utrons.dronehangarserver.service;

import com.utrons.dronehangarserver.comm.protocol.data.TextData;
import com.utrons.dronehangarserver.model.AppData;
import com.utrons.dronehangarserver.model.GetAllDataResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

	public GetAllDataResponse getAll(String sessionId) {
		GetAllDataResponse response = new GetAllDataResponse();
		response.setFlightData(AppData.getInstance().getFlightData());
		response.setHangarData(AppData.getInstance().getHangarData());
		response.setWeatherData(AppData.getInstance().getWeatherData());
		response.setWayPointData(AppData.getInstance().getWayPointData());

		List<TextData> textData = new ArrayList<>(AppData.getInstance().getTextData(sessionId));
		AppData.getInstance().clearWayPointData();
		response.setTextData(textData);
		return response;
	}
}
