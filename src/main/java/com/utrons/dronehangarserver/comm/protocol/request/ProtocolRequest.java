package com.utrons.dronehangarserver.comm.protocol.request;

import com.utrons.dronehangarserver.comm.protocol.model.ProtocolFrame;

public class ProtocolRequest extends ProtocolFrame {

//    public static ProtocolRequest build(byte pkgSeq, int uintCommand, byte[] pkgPayload) {
//        ProtocolRequest obj = new ProtocolRequest();
//        obj.setPkgSeq(pkgSeq);
//        obj.setUintCommand(uintCommand);
//        obj.setPkgLength(pkgPayload.length > 0 ? (byte) pkgPayload.length : 0);
//
//        byte[] data = new byte[pkgPayload.length + 9];
//        data[0] = START_FLAT_BIT_1;
//        data[1] = START_FLAT_BIT_2;
//        data[2] = pkgSeq;
//
//        byte[] pkgCommand = NumericUtil.intToBytes(uintCommand, 2, true);
//        data[3] = pkgCommand[0];
//        data[4] = pkgCommand[1];
//        data[5] = (byte) pkgPayload.length;
//        System.arraycopy(pkgPayload, 0, data, 6, pkgPayload.length);
//        obj.setUintChecksum(calChecksum(data, data.length - 3));
//        data[pkgPayload.length + 6] = obj.getPkgChecksum()[0];
//        data[pkgPayload.length + 7] = obj.getPkgChecksum()[1];
//        data[pkgPayload.length + 8] = STOP_FLAT_BIT;
//
//        obj.setBuff(data);
//        obj.setCount(data.length);
//
//        return obj;
//    }
}
