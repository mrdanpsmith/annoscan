package com.siirush.annoscan.provider;

import static org.junit.Assert.*;

import org.junit.Test;

import com.siirush.annoscan.AnnoScan;

public class AnnoScanProviderTest {
	@Test
	public void testGetReturnsSameInstance() {
		AnnoScan annoScan = AnnoScanProvider.instance.get();
		AnnoScan annoScan2 = AnnoScanProvider.instance.get();
		assertEquals(annoScan,annoScan2);
	}
}
