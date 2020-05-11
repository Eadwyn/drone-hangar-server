package com.utrons.dronehangarserver.comm.protocol.model;

import com.utrons.dronehangarserver.util.NumericUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 协议帧
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolFrame implements Serializable, Cloneable {
    private static final long serialVersionUID = -632475364129166928L;
    public static final int MAX_BUFF_SIZE = 275;

    //region 协议包内容
    /** 起始位 */
    public static final byte START_FLAT_BIT = (byte) 0xB2;
    /** 数据域长度 */
    protected byte pkgLength;
    /** 账号 */
    protected byte[] username;
    /** 密码 */
    protected byte[] password;
    /** 帧序号 */
    protected byte pkgSeq;
    /** 系统号 */
    protected byte pkgSystemId;
    /** 命令字（2字节） */
    protected byte[] pkgCommand = new byte[2];
    /** 数据域 */
    protected byte[] pkgPayload = new byte[0];
    /** 校验码（2字节） */
    protected byte[] pkgChecksum = new byte[2];
    //endregion

    //region 额外信息
    /** 数据包：状态机 */
    protected int state = 0;
    /** 数据包：缓存：保存整包数据信息。一包有效协议长度=20 + 数据域长度，数据域为1字节，即255，故这里定义长度=20 + 255 = 275 */
    protected byte[] buff = new byte[MAX_BUFF_SIZE];
    /** 数据包：有效长度 */
    protected int count = 0;
    /** 数据包：长度 */
    protected int uintLength;
    /** 数据包：帧序号 */
    protected int uintSeq;
    /** 数据包：系统id */
    protected int uintSystemId;
    /** 数据包：命令字 */
    protected int uintCommand;
    /** 数据包：校验码 */
    protected int uintChecksum;
    //endregion

    public void setPkgLength(byte length) {
        this.pkgLength = length;
        this.uintLength = NumericUtil.getUnSigned8(length);
    }

    public void setUintLength(int length) {
        this.uintLength = length;
        this.pkgLength = (byte) length;
    }

    public void setPkgSeq(byte seq) {
        this.pkgSeq = seq;
        this.uintSeq = NumericUtil.getUnSigned8(seq);
    }

    public void setUintSeq(int uintSeq) {
        this.uintSeq = uintSeq;
        this.pkgSeq = (byte) uintSeq;
    }

    public void setPkgSystemId(byte systemId) {
        this.pkgSystemId = systemId;
        this.uintSystemId = NumericUtil.getUnSigned8(systemId);
    }

    public void setUintSystemId(int uintSystemId) {
        this.uintSystemId = uintSystemId;
        this.pkgSystemId = (byte) uintSystemId;
    }

    public void setPkgCommand(byte[] command) {
        this.pkgCommand = command;
        this.uintCommand = NumericUtil.getUnSigned16(command, true);
    }

    public void setUintCommand(int command) {
        this.uintCommand = command;
        this.pkgCommand = NumericUtil.intToBytes(command, 2, true);
    }

    public void setPkgChecksum(byte[] checksum) {
        this.pkgChecksum = checksum;
        this.uintChecksum = NumericUtil.getUnSigned16(checksum, true);
    }

    public void setUintChecksum(int checksum) {
        this.uintChecksum = checksum;
        this.pkgChecksum = NumericUtil.intToBytes(checksum, 2, true);
    }

    /**
     * 计算校验码
     *
     * @param data
     * @param len
     * @return
     */
    public static int calChecksum(byte[] data, int len) {
        return calChecksum(data, 0, len);
    }

    public static int calChecksum(byte[] data, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += NumericUtil.getUnSigned8(data[i]);
        }
        return sum;
    }

    @Override
    protected ProtocolFrame clone() throws CloneNotSupportedException {
        ProtocolFrame obj = new ProtocolFrame();
        obj.pkgLength = this.pkgLength;
        obj.username = this.username;
        obj.password = this.password;
        obj.pkgSeq = this.pkgSeq;
        obj.pkgSystemId = this.pkgSystemId;
        System.arraycopy(this.pkgCommand, 0, obj.pkgCommand, 0, this.pkgCommand.length);
        System.arraycopy(this.pkgPayload, 0, obj.pkgPayload, 0, this.pkgPayload.length);
        System.arraycopy(this.pkgChecksum, 0, obj.pkgChecksum, 0, this.pkgChecksum.length);

        obj.state = this.state;
        System.arraycopy(this.buff, 0, obj.buff, 0, this.buff.length);
        obj.count = this.count;
        obj.uintLength = this.uintLength;
        obj.uintSeq = this.uintSeq;
        obj.uintSystemId = this.uintSystemId;
        obj.uintCommand = this.uintCommand;
        obj.uintChecksum = this.uintChecksum;
        return obj;
    }

    public static void main(String[] args) {
        byte[] data = new byte[] {(byte)0xB2, (byte)0x04, (byte)0x61, (byte)0x61, (byte)0x61, (byte)0x61, (byte)0x61, (byte)0x61, (byte)0x31, (byte)0x31, (byte)0x31, (byte)0x31, (byte)0x31, (byte)0x31, (byte)0x00, (byte)0x00, (byte)0x08, (byte)0x08, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x81, (byte)0x03};
        System.out.println(calChecksum(data, 1, data.length - 2));

    }
}
