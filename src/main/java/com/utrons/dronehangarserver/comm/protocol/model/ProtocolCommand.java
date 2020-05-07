package com.utrons.dronehangarserver.comm.protocol.model;

public class ProtocolCommand {
	/** 心跳包 */
	public static final int WEB_HEART_CMD = 0x0801;
	/** 起飞命令 */
	public static final int WEB_TAKEOFF_CMD = 0x0802;
	/** 降落命令 */
	public static final int WEB_LANDING_CMD = 0x0803;
	/** 返航命令 */
	public static final int WEB_RETURN_CMD = 0x0804;
	/** 暂停航线命令 */
	public static final int WEB_PAUSE_CMD = 0x0805;
	/** 恢复航线命令 */
	public static final int WEB_RESUME_CMD = 0x0806;
	/** 自动航线命令 */
	public static final int WEB_AUTO_CMD = 0x0807;
	/** 航线选择 */
	public static final int WEB_WAYLINE_CMD = 0x0808;
	/** 飞行控制 */
	public static final int WEB_FLIGHT_CMD = 0x0809;
	/** 云台控制 */
	public static final int WEB_GIMBAL_CMD = 0x080a;
	/** 停机坪准备起飞流程 */
	public static final int WEB_HANGAR_START = 0x080b;
	/** 停机坪自动充电流程 */
	public static final int WEB_HANGAR_CHARGER = 0x080c;
	/** 停机坪休眠流程 */
	public static final int WEB_HANGAR_SLEEP = 0x080d;
	/** 停止流程 */
	public static final int WEB_HANGAR_STOP = 0x080e;
	/** 文本显示数据包 */
	public static final int WEB_TEXT_DATA = 0x080f;
	/** 飞行数据包 */
	public static final int WEB_FLIGHT_DATA = 0x0810;
	/** 停机坪数据包 */
	public static final int WEB_HANGAR_DATA = 0x0811;
	/** 气象数据包 */
	public static final int WEB_WEATHER_DATA = 0x0812;
	/** 航点数据 */
	public static final int WEB_WAYPOINT_DATA = 0x0813;
}
