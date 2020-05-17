package com.utrons.dronehangarserver.rest;

import com.utrons.dronehangarserver.model.AppData;
import com.utrons.dronehangarserver.model.GetAllDataResponse;
import com.utrons.dronehangarserver.model.request.EmptyRequest;
import com.utrons.dronehangarserver.model.response.ResponseHelper;
import com.utrons.dronehangarserver.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataRest extends BaseRest {
	@Autowired
	private DataService dataService;

	@PostMapping("/getAll")
	public ResponseHelper<GetAllDataResponse> getAll(@RequestBody EmptyRequest request) {
		AppData.getInstance().addClient(this.getSession());
		GetAllDataResponse data = this.dataService.getAll(this.getSession().getId());
		return ResponseHelper.newSuccess(data);
	}
}
