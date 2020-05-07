package com.utrons.dronehangarserver.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 数值工具类
 *
 * @author Eadwyn
 * @since
 * @see
 */
public class NumericUtil {

	/**
	 * 将val转换成无符号数值。<br />
	 * 注意：val只能表示32位或以下的数据。
	 *
	 * @param val
	 *            要进行转换的数据
	 * @return
	 */
	public static long getUnSigned32(long val) {
		int byte_1 = (0x000000FF & ((int) (val >> 0)));
		int byte_2 = (0x000000FF & ((int) (val >> 8)));
		int byte_3 = (0x000000FF & ((int) (val >> 16)));
		int byte_4 = (0x000000FF & ((int) (val >> 24)));
		return ((long) (byte_1 | byte_2 << 8 | byte_3 << 16 | byte_4 << 24)) & 0xFFFFFFFFL;
	}

	/**
	 * 将val转换成无符号数值。<br />
	 * 注意：val只能表示16位或以下的数据。
	 *
	 * @param val
	 *            要进行转换的数据
	 * @return
	 */
	public static int getUnSigned16(int val) {
		int byte_1 = (0x00FF & ((int) (val >> 0)));
		int byte_2 = (0x000000FF & ((int) (val >> 8)));
		return ((int) (byte_1 | byte_2 << 8)) & 0xFFFF;
	}

	/**
	 * 把16进制字符串转换成字节数组。每个hex之间没有空格（或其他符号）分隔
	 *
	 * @param hex
	 *            要转换的16进制字符串
	 *
	 * @return 返回转换后的字节数组
	 */
	public static byte[] hexStringToBytes(String hex) {
		hex = hex.toUpperCase();
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 把字节数组转换成16进制字符串。每个hex之间使用空格分隔
	 *
	 * @param bArray
	 *            要转换的字节数组
	 * @return 返回转换后的16进制字符串
	 *
	 *
	 */
	public static final String bytesToHexStringWithBlank(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase() + " ");
		}
		return sb.toString();
	}

	/**
	 * 把字节数组转换成16进制字符串。每个hex之间没有空格（或其他字符）分隔
	 *
	 * @param bArray
	 *            要转换的字节数组
	 * @return 返回转换后的16进制字符串
	 *
	 *
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static final String byteToHexString(byte b) {
		StringBuffer sb = new StringBuffer();
		String sTemp;
		sTemp = Integer.toHexString(0xFF & b);
		if (sTemp.length() < 2)
			sb.append(0);
		sb.append(sTemp.toUpperCase());
		return sb.toString();
	}

	/**
	 * 把字节数组转换为对象
	 *
	 * @param bArray
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static final Object bytesToObject(byte[] bArray) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(bArray);
		ObjectInputStream oi = new ObjectInputStream(in);
		Object o = oi.readObject();
		oi.close();
		return o;
	}

	/**
	 * 把可序列化对象转换成字节数组
	 *
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static final byte[] objectToBytes(Serializable s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream ot = new ObjectOutputStream(out);
		ot.writeObject(s);
		ot.flush();
		ot.close();
		return out.toByteArray();
	}

	public static final String objectToHexString(Serializable s) throws IOException {
		return bytesToHexString(objectToBytes(s));
	}

	public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException {
		return bytesToObject(hexStringToBytes(hex));
	}

	/**
	 * BCD码转为10进制串(阿拉伯数据)
	 *
	 * @param bytes
	 *            BCD码
	 *
	 * @return 10进制串
	 *
	 */
	public static String bcdToString(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}

	/**
	 * 10进制串转为BCD码
	 *
	 *
	 * @param asc
	 *            10进制串
	 *
	 * @return BCD码
	 *
	 */
	public static byte[] stringToBcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/**
	 * 将val转换字节表示
	 *
	 * @param val 要转换的数值
	 * @param len 字节数组长度
	 * @param isLowByteInFront true - 低字节在前；false - 高字节在前
	 * @return
	 */
	public static byte[] intToBytes(int val, int len, boolean isLowByteInFront) {
		byte[] result = new byte[len];
		if (isLowByteInFront) {
			switch (len) {
				case 1:
					result[0] = (byte)(val & 0x000000FF);
					break;
				case 2:
					result[0] = (byte)(val & 0x000000FF);
					result[1] = (byte)((val & 0x0000FF00) >> 8);
					break;
				case 3:
					result[0] = (byte)(val & 0x000000FF);
					result[1] = (byte)((val & 0x0000FF00) >> 8);
					result[2] = (byte)((val & 0x00FF0000) >> 16);
					break;
				case 4:
					result[0] = (byte)(val & 0x000000FF);
					result[1] = (byte)((val & 0x0000FF00) >> 8);
					result[2] = (byte)((val & 0x00FF0000) >> 16);
					result[3] = (byte)((val & 0xFF000000) >> 24);
					break;
			}
		} else {
			switch (len) {
				case 1:
					result[0] = (byte)((val & 0xFF000000) >> 24);
					break;
				case 2:
					result[0] = (byte)((val & 0xFF000000) >> 24);
					result[1] = (byte)((val & 0x00FF0000) >> 16);
					break;
				case 3:
					result[0] = (byte)((val & 0xFF000000) >> 24);
					result[1] = (byte)((val & 0x00FF0000) >> 16);
					result[2] = (byte)((val & 0x0000FF00) >> 8);
					break;
				case 4:
					result[0] = (byte)((val & 0xFF000000) >> 24);
					result[1] = (byte)((val & 0x00FF0000) >> 16);
					result[2] = (byte)((val & 0x0000FF00) >> 8);
					result[3] = (byte)(val & 0x000000FF);
					break;
			}
		}
		return result;
	}

	/**
	 * 无符号转byte
	 *
	 * @param val
	 * @return
	 */
	public static byte unSigned8ToByte(int val) {
		return (byte) val;
	}

	// ===================================================================================
	/**
	 * 将指定的字节数组转换成32位无符号整数。
	 * 注意：val应该为长度为4的数组。如果长度小于4，则不足部分以0补充；若长度大于4，则只取前4位
	 *
	 * @param val 要进行转换的字节数据
	 * @param isLowByteInFront true - 低字节在前； false - 高字节在前
	 * @return
	 */
	public static long getUnSigned32(byte[] val, boolean isLowByteInFront) {
		byte[] tmp = new byte[] {0, 0, 0, 0};
		for (int i = 0, len = val.length; i < len; i++) {
			if (i >= 4) {
				break;
			}
			tmp[i] = val[i];
		}

		if (isLowByteInFront) {
			return ((tmp[0] & 0xFFL) + ((tmp[1] & 0xFFL) << 8) + ((tmp[2] & 0xFFL) << 16) + ((tmp[3] & 0xFFL) << 24));
		} else {
			return ((tmp[3] & 0xFFL) + ((tmp[2] & 0xFFL) << 8) + ((tmp[1] & 0xFFL) << 16) + ((tmp[0] & 0xFFL) << 24));
		}
	}

	/**
	 * 将指定的字节数组转换成16位无符号整数。
	 * 注意：val应该为长度为2的数组。如果长度小于2，则不足部分以0补充；若长度大于2，则只取前2位
	 *
	 * @param val 要进行转换的字节数据
	 * @param isLowByteInFront true - 低字节在前； false - 高字节在前
	 * @return
	 */
	public static int getUnSigned16(byte[] val, boolean isLowByteInFront) {
		byte[] tmp = new byte[] {0, 0};
		for (int i = 0, len = val.length; i < len; i++) {
			if (i >= 2) {
				break;
			}
			tmp[i] = val[i];
		}

		if (isLowByteInFront) {
			return ((tmp[0] & 0xFF) + ((tmp[1] & 0xFF) << 8));
		} else {
			return ((tmp[1] & 0xFF) + ((tmp[0] & 0xFF) << 8));
		}
	}

	/**
	 * 将指定的字节转换成8位无符号整数。
	 *
	 * @param val 要进行转换的字节
	 * @return
	 */
	public static short getUnSigned8(byte val) {
		return (short)(val & 0xFF);
	}

	/**
	 * 将指定的字节数组转换成32位有符号整数。
	 * 注意：val应该为长度为4的数组。如果长度小于4，则不足部分以0补充；若长度大于4，则只取前4位
	 *
	 * @param val 要进行转换的字节数据
	 * @param isLowByteInFront true - 低字节在前； false - 高字节在前
	 * @return
	 */
	public static int getSigned32(byte[] val, boolean isLowByteInFront) {
		byte[] tmp = new byte[] {0, 0, 0, 0};
		for (int i = 0, len = val.length; i < len; i++) {
			if (i >= 4) {
				break;
			}
			tmp[i] = val[i];
		}

		if (isLowByteInFront) {
			return ((tmp[0] & 0xFF) + ((tmp[1] & 0xFF) << 8) + ((tmp[2] & 0xFF) << 16) + ((tmp[3] & 0xFF) << 24));
		} else {
			return ((tmp[3] & 0xFF) + ((tmp[2] & 0xFF) << 8) + ((tmp[1] & 0xFF) << 16) + ((tmp[0] & 0xFF) << 24));
		}
	}

	/**
	 * 将指定的字节数组转换成16位有有符号整数。
	 * 注意：val应该为长度为2的数组。如果长度小于2，则不足部分以0补充；若长度大于2，则只取前2位
	 *
	 * @param val 要进行转换的字节数据
	 * @param isLowByteInFront true - 低字节在前； false - 高字节在前
	 * @return
	 */
	public static short getSigned16(byte[] val, boolean isLowByteInFront) {
		byte[] tmp = new byte[] {0, 0};
		for (int i = 0, len = val.length; i < len; i++) {
			if (i >= 2) {
				break;
			}
			tmp[i] = val[i];
		}

		if (isLowByteInFront) {
			return (short)((tmp[0] & 0xFF)  + ((tmp[1] & 0xFF) << 8));
		} else {
			return (short)((tmp[1] & 0xFF) + ((tmp[0] & 0xFF) << 8));
		}
	}

	public static void main(String[] args) {
		byte[] val = new byte[] {(byte)0xD5, (byte)0x50, (byte)0x8A, (byte)0x0D};

		System.out.println(getUnSigned32(val, true));
		System.out.println(getSigned32(val, true));
	}
	// ===================================================================================
	// 关于byte:
	// signed byte 把 0x00 ~ 0xff 映射成范围 0~127和 -128~-1两段,
	// 比较简单的办法用 (b+256)%256的办法令其值回到0~255，或者用&0xff并赋给一个int。

	// 参考http://www.jsfsoft.com:8080/beyond-pebble/pinxue/2006/08/23/1156309692525.html
}

