package com.utrons.dronehangarserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadWayPointRequest implements Serializable {
	private static final long serialVersionUID = 5601320865826371116L;
	private Integer code;
}
