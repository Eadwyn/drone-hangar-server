package com.utrons.dronehangarserver.model;

import com.utrons.dronehangarserver.comm.protocol.data.TextData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Vector;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebClientData implements Serializable {
	private static final long serialVersionUID = -115380651867398057L;
	private HttpSession session;
	private Vector<TextData> textData = new Vector<>();
}
