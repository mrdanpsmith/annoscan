package com.siirush.annoscan.provider;

import javax.inject.Provider;

import com.siirush.annoscan.AnnoScan;
import com.siirush.annoscan.AnnoScanImpl;
import com.siirush.annoscan.reflection.GetterLocatorImpl;

public class AnnoScanProvider implements Provider<AnnoScan> {
	public static final AnnoScanProvider instance = new AnnoScanProvider();
	private final AnnoScan annoScan;
	private AnnoScanProvider() {
		annoScan = new AnnoScanImpl(new GetterLocatorImpl());
	}
	/* (non-Javadoc)
	 * @see javax.inject.Provider#get()
	 */
	public AnnoScan get() {
		return annoScan;
	}
}
