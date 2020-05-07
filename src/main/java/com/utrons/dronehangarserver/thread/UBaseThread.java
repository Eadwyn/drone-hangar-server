package com.utrons.dronehangarserver.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程基础类
 */
@Slf4j
public abstract class UBaseThread {
	private ThreadState state = ThreadState.READY;
	private UBaseThread instance;

	/**
	 * 启动线程
	 */
	public synchronized void start() {
		if (null != instance && instance.state == ThreadState.RUNNING) {
			return;
		}

		instance = this.newInstance();
		instance.state = ThreadState.RUNNING;
		new Thread(new URunnable(instance)).start();
	}

	/**
	 * 终止线程
	 */
	public synchronized void stop() {
		instance.state = ThreadState.STOP;
	}

	/**
	 * 重启线程
	 */
	public synchronized void restart() {
		stop();
		start();
	}

	/**
	 * 获取线程状态
	 *
	 * @return
	 */
	public synchronized ThreadState getState() {
		return instance.state;
	}

	/**
	 * 创建新实例
	 *
	 * @return
	 */
	protected abstract UBaseThread newInstance();

	/**
	 * 线程具体的工作内容
	 */
	protected abstract void worker();

	/**
	 * 线程状态
	 */
	public enum ThreadState {
		/**
		 * 未启动
		 */
		READY,
		/**
		 * 正在运行
		 */
		RUNNING,
		/**
		 * 已停止
		 */
		STOP
	}

	class URunnable implements Runnable {
		UBaseThread ins;

		public URunnable(UBaseThread ins) {
			this.ins = ins;
		}

		@Override
		public void run() {
			log.info("线程开始运行：" + Thread.currentThread().getName());
			while (ins.state == ThreadState.RUNNING) {
				worker();
			}
			log.info("线程结束运行：" + Thread.currentThread().getName());
		}
	}
}