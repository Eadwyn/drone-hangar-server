package com.utrons.dronehangarserver.rest;


import com.utrons.dronehangarserver.model.User;
import com.utrons.dronehangarserver.model.response.EmptyResponse;
import com.utrons.dronehangarserver.model.response.ResponseHelper;
import com.utrons.dronehangarserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TokenRest {
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseHelper<EmptyResponse> login(@RequestBody User request) {
		this.tokenService.login(request);
		return ResponseHelper.newSuccess();
	}
}
