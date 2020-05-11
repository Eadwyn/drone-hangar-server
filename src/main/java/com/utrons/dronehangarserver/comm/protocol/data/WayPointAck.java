package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WayPointAck implements Serializable {

	/** 【U16】包序列号,发完一包累加 1 */
	private int seq;
	/** 【U16】航线包含的航点数 */
	private int total;

	public static WayPointAck parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		WayPointAck obj = new WayPointAck();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setTotal(NumericUtil.getUnSigned16(new byte[]{data[2], data[3]}, true));
		return obj;
	}
}
