function refreshWeatherData(weatherData) {
	$("#hangarLongitudeValue").html(weatherData.longitude);
	$("#hangarLatitudeValue").html(weatherData.latitude);
	$("#satelliteValue").html(weatherData.satellite);
	$("#windSpeedValue").html(weatherData.windSpeed);
	$("#windDirectionValue").html(weatherData.windDirection);
	$("#temperatureValue").html(weatherData.temperature);
	$("#humidityValue").html(weatherData.humidity);
	$("#lightValue").html(weatherData.light);
	if (weatherData.rain) {
		$("#rainValue").html("有雨");
	} else {
		$("#rainValue").html("无雨");
	}
}

function refreshHangarData(hangarData) {
	switch (hangarData.cover) {
		case 1:
			$("#coverValue").html("关闭");
			break;
		case 2:
			$("#coverValue").html("打开");
			break;
		case 3:
			$("#coverValue").html("正在关闭");
			break;
		case 4:
			$("#coverValue").html("正在打开");
			break;
		default:
			$("#coverValue").html("未知");
	}
	switch (hangarData.folder) {
		case 1:
			$("#folderValue").html("夹紧");
			break;
		case 2:
			$("#folderValue").html("放松");
			break;
		case 3:
			$("#folderValue").html("运动过程");
			break;
		default:
			$("#folderValue").html("未知");
	}
	switch (hangarData.lifts) {
		case 1:
			$("#liftsValue").html("降下");
			break;
		case 2:
			$("#liftsValue").html("升起");
			break;
		case 3:
			$("#liftsValue").html("降下过程");
			break;
		case 4:
			$("#liftsValue").html("升起过程");
			break;
		default:
			$("#liftsValue").html("未知");
	}
	var setValueStatus = function (obj, status) {
		if (status) {
			obj.html("打开");
		} else {
			obj.html("关闭");
		}
	};
	setValueStatus($("#inLampValue"), hangarData.inLampOn);
	setValueStatus($("#outLampValue"), hangarData.outLampOn);
	setValueStatus($("#hangarPowerValue"), hangarData.hangarPowerOn);
	setValueStatus($("#chargerPowerValue"), hangarData.chargerPowerOn);
	setValueStatus($("#rfPowerValue"), hangarData.rfPowerOn);
	setValueStatus($("#cameraPowerValue"), hangarData.cameraPowerOn);
	setValueStatus($("#remotePowerValue"), hangarData.remotePowerOn);
	setValueStatus($("#rtkPowerValue"), hangarData.rtkPowerOn);
	setValueStatus($("#screenPowerValue"), hangarData.screenPowerOn);
	setValueStatus($("#speakerPowerValue"), hangarData.speakerPowerOn);
	setValueStatus($("#driverPowerValue"), hangarData.driverPowerOn);
}

function setFlightMode(mode) {
	switch (mode) {
		case 0:
			$("#flightModeValue").html("MANUAL");
			break;
		case 1:
			$("#flightModeValue").html("ATTI");
			break;
		case 2:
			$("#flightModeValue").html("ATTI_COURSE_LOCK");
			break;
		case 3:
			$("#flightModeValue").html("ATTI_HOVER");
			break;
		case 4:
			$("#flightModeValue").html("HOVER");
			break;
		case 5:
			$("#flightModeValue").html("GPS_BLAKE");
			break;
		case 6:
			$("#flightModeValue").html("GPS_ATTI");
			break;
		case 7:
			$("#flightModeValue").html("GPS_COURSE_LOCK");
			break;
		case 8:
			$("#flightModeValue").html("GPS_HOME_LOCK");
			break;
		case 9:
			$("#flightModeValue").html("GPS_HOT_POINT");
			break;
		case 10:
			$("#flightModeValue").html("ASSISTED_TAKEOFF");
			break;
		case 11:
			$("#flightModeValue").html("AUTO_TAKEOFF");
			break;
		case 12:
			$("#flightModeValue").html("AUTO_LANDING");
			break;
		case 13:
			$("#flightModeValue").html("ATTI_LANDING");
			break;
		case 14:
			$("#flightModeValue").html("GPS_WAYPOINT");
			break;
		case 15:
			$("#flightModeValue").html("GO_HOME");
			break;
		case 16:
			$("#flightModeValue").html("CLICK_GO");
			break;
		case 17:
			$("#flightModeValue").html("JOYSTICK");
			break;
		case 18:
			$("#flightModeValue").html("GPS_ATTI_WRISTBAND");
			break;
		case 19:
			$("#flightModeValue").html("CINEMATIC");
			break;
		case 23:
			$("#flightModeValue").html("ATTI_LIMITED");
			break;
		case 24:
			$("#flightModeValue").html("DRAW");
			break;
		case 25:
			$("#flightModeValue").html("GPS_FOLLOW_ME");
			break;
		case 26:
			$("#flightModeValue").html("ACTIVE_TRACK");
			break;
		case 27:
			$("#flightModeValue").html("TAP_FLY");
			break;
		case 28:
			$("#flightModeValue").html("PANO");
			break;
		case 29:
			$("#flightModeValue").html("FARMING");
			break;
		case 30:
			$("#flightModeValue").html("FPV");
			break;
		case 31:
			$("#flightModeValue").html("GPS_SPORT");
			break;
		case 32:
			$("#flightModeValue").html("GPS_NOVICE");
			break;
		case 33:
			$("#flightModeValue").html("CONFIRM_LANDING");
			break;
		case 35:
			$("#flightModeValue").html("TERRAIN_FOLLOW");
			break;
		case 36:
			$("#flightModeValue").html("PALM_CONTROL");
			break;
		case 37:
			$("#flightModeValue").html("QUICK_SHOT");
			break;
		case 38:
			$("#flightModeValue").html("TRIPOD");
			break;
		case 39:
			$("#flightModeValue").html("TRACK_SPOTLIGHT");
			break;
		case 41:
			$("#flightModeValue").html("MOTORS_JUST_STARTED");
			break;
		case 43:
			$("#flightModeValue").html("DETOUR");
			break;
		case 46:
			$("#flightModeValue").html("TIME_LAPSE");
			break;
		case 50:
			$("#flightModeValue").html("POI2");
			break;
		case 49:
			$("#flightModeValue").html("OMNI_MOVING");
			break;
		case 41:
			$("#flightModeValue").html("ADSB_AVOIDING");
			break;
		case 255:
		default:
			$("#flightModeValue").html("未知");
	}
}

function refreshFlightData(flightData) {
	$("#rssiValue").html(flightData.rssi);
	$("#rollValue").html(flightData.roll);
	$("#pitchValue").html(flightData.pitch);
	$("#yawValue").html(flightData.yaw);
	$("#hightValue").html(flightData.hight);
	$("#distanceValue").html(flightData.distance);
	$("#eastSpeedValue").html(flightData.eastSpeed);
	$("#northSpeedValue").html(flightData.northSpeed);
	$("#upSpeedValue").html(flightData.upSpeed);
	$("#fixTypeValue").html(flightData.fixType);
	$("#voltageValue").html(flightData.voltage);
	$("#currentValue").html(flightData.current);
	$("#percentageValue").html(flightData.percentage);
	$("#gimbalYawValue").html(flightData.gimbalYaw);
	$("#gimbalPitchValue").html(flightData.gimbalPitch);
	$("#xxxxxxcameraFoceValuexx").html(flightData.cameraFoce);
	$("#cameraStatusValue").html(flightData.cameraStatus);
	setFlightMode(flightData.flightMode);
	if (flightData.rtkConnected) {
		$("#rtkConnectedValue").html("已连接");
	} else {
		$("#rtkConnectedValue").html("未连接");
	}
	if (flightData.motorsOn) {
		$("#motorsValue").html("开");
	} else {
		$("#motorsValue").html("关");
	}
	if (flightData.wayPointReady) {
		$("#wayPointReadyValue").html("是");
	} else {
		$("#wayPointReadyValue").html("否");
	}

	if (flightData.beingCharged) {
		$("#beingChargedValue").html("充电模式");
	} else {
		$("#beingChargedValue").html("用电模式");
	}
	setPosition(flightData.longitude, flightData.latitude);
}

function refreshTextData(textData) {
	var logPanel = $("#logContainer ul");
	for (var i = 0, len = textData.length; i < len; i++) {
		logPanel.html(logPanel.html() + "<li>[" + textData[i].time + "] " + textData[i].text + "</li>");
	}
	$("#logContainer").scrollTop($("#logContainer").prop('scrollHeight'));
}

function refreshWayPointData(wayPointData) {
	if (!map || !wayPointData || wayPointData.length == 0) {
		return;
	}
	if (polylines) {
		map.remove(polylines);
	}
	polylines.length = 0;
	var path = [];
	for (var i = 0, len = wayPointData.length; i < len; i++) {
		path.push([wayPointData.longitude, wayPointData.latitude]);
	}
	var line = new AMap.Polyline({
		path: path,
		isOutline: true,
		outlineColor: "#FF0000",
		borderWeight: 3,
		strokeColor: "#FF0000",
		strokeOpacity: 1,
		strokeWeight: 3,
		strokeStyle: "solid",
		strokeDasharray: [10, 5],
		lineJoin: "round",
		lineCap: "round",
		zIndex: 50,
	});
	polylines.push(line);
	map.add(polylines);
	map.setFitView();
}

function refreshData() {
	Ajax.post("/data/getAll", {}, function (response) {
		if (response.code == "0") {
			refreshWeatherData(response.data.weatherData);
			refreshHangarData(response.data.hangarData);
			refreshFlightData(response.data.flightData);
			refreshTextData(response.data.textData);
			refreshWayPointData(response.data.wayPointData);
		} else {
			alert(response.message);
		}
	});
}

function initRefreshData() {
	window.setInterval(function () {
		refreshData();
	}, 1000);
}