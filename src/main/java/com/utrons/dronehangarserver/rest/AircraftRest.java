package com.utrons.dronehangarserver.rest;

import com.utrons.dronehangarserver.model.request.EmptyRequest;
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

	//region 起飞
	@PostMapping("/takeOff")
	public ResponseHelper<EmptyResponse> takeOff(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 降落
	@PostMapping("/landing")
	public ResponseHelper<EmptyResponse> landing(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 返航
	@PostMapping("/returnHome")
	public ResponseHelper<EmptyResponse> returnHome(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 暂停航线
	@PostMapping("/pause")
	public ResponseHelper<EmptyResponse> pause(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 恢复航线
	@PostMapping("/resume")
	public ResponseHelper<EmptyResponse> resume(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 自动航线
	@PostMapping("/auto")
	public ResponseHelper<EmptyResponse> auto(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 自动充电
	@PostMapping("/charger")
	public ResponseHelper<EmptyResponse> charger(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 准备起飞
	@PostMapping("/hangarStart")
	public ResponseHelper<EmptyResponse> hangarStart(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 停止流程
	@PostMapping("/hangerStop")
	public ResponseHelper<EmptyResponse> hangerStop(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion

	//region 休眠流程
	@PostMapping("/hangarSleep")
	public ResponseHelper<EmptyResponse> hangarSleep(@RequestBody EmptyRequest request) {
		return ResponseHelper.newSuccess();
	}
	//endregion
}
