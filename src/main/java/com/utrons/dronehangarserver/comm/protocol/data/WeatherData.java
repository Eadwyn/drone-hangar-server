package com.utrons.dronehangarserver.comm.protocol.data;

import com.utrons.dronehangarserver.comm.protocol.request.ProtocolRequest;
import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeatherData implements Serializable {

	private static final long serialVersionUID = 6012533561341517066L;
	/** 【U16】包序列号,发完一包累加1 */
	private int seq;
	/** 【S32】经度，实际值= Longitude/10000000,单位 deg */
	private double longitude;
	/** 【S32】纬度，实际值= Latitude /10000000,单位 deg */
	private double latitude;
	/** 【U8】卫星数 */
	private int satellite;
	/** 【U16】风速，实际值= WindSpeed/10.0f,单位 m/s */
	private float windSpeed;
	/** 【U16】风向，实际值= WindDirection /10.0f,单位：deg */
	private float windDirection;
	/** 【S16】温度，实际值= Temperature /10.0f,单位：C */
	private float temperature;
	/** 【U16】湿度，实际值= Humidity /10.0f,单位：% */
	private float humidity;
	/** 【U16】光照，实际值= Light /10.0f,单位：lux */
	private float light;
	/** 【U16】雨量，0-无雨，1-有雨 */
	private boolean isRain;


	public static WeatherData parse(ProtocolRequest request) {
		byte[] data = request.getPkgPayload();

		WeatherData obj = new WeatherData();
		obj.setSeq(NumericUtil.getUnSigned16(new byte[]{data[0], data[1]}, true));
		obj.setLongitude(NumericUtil.getSigned32(new byte[]{data[2], data[3], data[4], data[5]}, true) / 10000000.0);
		obj.setLatitude(NumericUtil.getSigned32(new byte[]{data[6], data[7], data[8], data[9]}, true) / 10000000.0);
		obj.setSatellite(NumericUtil.getUnSigned8(data[10]));
		obj.setWindSpeed(NumericUtil.getUnSigned16(new byte[]{data[11], data[12]}, true) / 10.0f);
		obj.setWindDirection(NumericUtil.getUnSigned16(new byte[]{data[13], data[14]}, true) / 10.0f);
		obj.setTemperature(NumericUtil.getSigned16(new byte[]{data[15], data[16]}, true) / 10.0f);
		obj.setHumidity(NumericUtil.getUnSigned16(new byte[]{data[17], data[18]}, true) / 10.0f);
		obj.setLight(NumericUtil.getUnSigned16(new byte[]{data[19], data[20]}, true) / 10.0f);
		obj.setRain(NumericUtil.getUnSigned16(new byte[]{data[21], data[22]}, true) == 1 ? true : false);

		return obj;
	}
}
