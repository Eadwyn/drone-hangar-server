package com.utrons.dronehangarserver.thread;

import com.utrons.dronehangarserver.comm.CommData;
import com.utrons.dronehangarserver.comm.CommDataParser;

/**
 * 已接收数据处理线程
 */
public class CommReceivedHandlerThread extends UBaseThread {
    private static CommReceivedHandlerThread instance;

    private CommReceivedHandlerThread() {
    }

    public static CommReceivedHandlerThread getInstance() {
        if (null == instance) {
            synchronized (CommReceivedHandlerThread.class) {
                if (null == instance) {
                    instance = new CommReceivedHandlerThread();
                }
            }
        }
        return instance;
    }

    @Override
    protected UBaseThread newInstance() {
        return new CommReceivedHandlerThread();
    }

    @Override
    protected void worker() {
        byte[] data = CommData.getReceived();
        if (null != data) {
            CommDataParser.handleProtocolData(data, data.length);
        }
    }
}
