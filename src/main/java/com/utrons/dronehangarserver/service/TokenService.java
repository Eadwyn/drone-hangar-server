package com.utrons.dronehangarserver.service;

import com.utrons.dronehangarserver.model.AppData;
import com.utrons.dronehangarserver.model.User;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	public void login(User user) {
		byte[] usernameSrc = user.getUsername().getBytes();
		byte[] usernameDest = new byte[6];
		byte[] passwordSrc = user.getPassword().getBytes();
		byte[] passwordDest = new byte[6];

		if (usernameSrc.length >= 6) {
			System.arraycopy(usernameSrc, 0, usernameDest, 0, 6);
		} else {
			System.arraycopy(usernameSrc, 0, usernameDest, 0, usernameSrc.length);
		}

		if (passwordSrc.length >= 6) {
			System.arraycopy(passwordSrc, 0, passwordDest, 0, 6);
		} else {
			System.arraycopy(passwordSrc, 0, passwordDest, 0, passwordSrc.length);
		}

		AppData.getInstance().setUsername(usernameDest);
		AppData.getInstance().setPassword(passwordDest);
	}
}
