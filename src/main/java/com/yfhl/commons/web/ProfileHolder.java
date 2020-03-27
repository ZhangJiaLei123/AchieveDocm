package com.yfhl.commons.web;

import com.yfhl.commons.domain.BizProcess;


public class ProfileHolder {
	private static InheritableThreadLocal<BizProcess> profileThread = new InheritableThreadLocal<BizProcess>();

	public ProfileHolder() {

	}

	public static BizProcess getBizProcess() {
		return profileThread.get();
	}

	/**
	 * Add the BizProcess to the ThreadLocal.
	 *
	 * @param bizProcess
	 *            the bizProcess to add.
	 */
	public static void setBizProcess(final BizProcess bizProcess) {
		profileThread.set(bizProcess);
	}

	/**
	 * Clear the ThreadLocal.
	 */
	public static void clear() {
		profileThread.set(null);
	}
}
