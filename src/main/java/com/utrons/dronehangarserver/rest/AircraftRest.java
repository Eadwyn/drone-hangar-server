package com.utrons.dronehangarserver.rest;

import com.utrons.dronehangarserver.model.request.EmptyRequest;
import com.utrons.dronehangarserver.model.request.LoadWayPointRequest;
import com.utrons.dronehangarserver.model.response.EmptyResponse;
import com.utrons.dronehangarserver.model.response.ResponseHelper;
import com.utrons.dronehangarserver.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aircrafts")
public class AircraftRest {
	@Autowired
	private AircraftService aircraftService;

	//region 控制命令
	//region 起飞
	@PostMapping("/takeOff")
	public ResponseHelper<EmptyResponse> takeOff(@RequestBody EmptyRequest request) {
		this.aircraftService.takeOff();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 降落
	@PostMapping("/landing")
	public ResponseHelper<EmptyResponse> landing(@RequestBody EmptyRequest request) {
		this.aircraftService.landing();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 返航
	@PostMapping("/returnHome")
	public ResponseHelper<EmptyResponse> returnHome(@RequestBody EmptyRequest request) {
		this.aircraftService.landing();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 暂停航线
	@PostMapping("/pause")
	public ResponseHelper<EmptyResponse> pause(@RequestBody EmptyRequest request) {
		this.aircraftService.pause();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 恢复航线
	@PostMapping("/resume")
	public ResponseHelper<EmptyResponse> resume(@RequestBody EmptyRequest request) {
		this.aircraftService.resume();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 自动航线
	@PostMapping("/auto")
	public ResponseHelper<EmptyResponse> auto(@RequestBody EmptyRequest request) {
		this.aircraftService.auto();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 自动充电
	@PostMapping("/charger")
	public ResponseHelper<EmptyResponse> charger(@RequestBody EmptyRequest request) {
		this.aircraftService.charger();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 准备起飞
	@PostMapping("/hangarStart")
	public ResponseHelper<EmptyResponse> hangarStart(@RequestBody EmptyRequest request) {
		this.aircraftService.hangarStart();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 停止流程
	@PostMapping("/hangerStop")
	public ResponseHelper<EmptyResponse> hangerStop(@RequestBody EmptyRequest request) {
		this.aircraftService.hangerStop();
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 休眠流程
	@PostMapping("/hangarSleep")
	public ResponseHelper<EmptyResponse> hangarSleep(@RequestBody EmptyRequest request) {
		this.aircraftService.hangarSleep();
		return ResponseHelper.newSuccess();
	}
	//endregion
	//endregion

	//region 加载航线
	@PostMapping("/loadWayPoint")
	public ResponseHelper<EmptyResponse> loadWayPoint(@RequestBody LoadWayPointRequest request) {
		this.aircraftService.loadWayPoint(request.getCode());
		return ResponseHelper.newSuccess();
	}
	//endregion

}
