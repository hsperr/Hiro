package hiro.filter;

import hiro.filter.MelFilterBank;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MelFilterBankTest {
	static MelFilterBank mfb = null;

	@Test
	public void testMelFilterBank() {
		mfb = new MelFilterBank(2, 0, 400);
		mfb.setSamplingRate(600);
		mfb.setFFTSize(3);

		List<List<Double>> l = new ArrayList<List<Double>>();
		List<Double> window1 = new ArrayList<Double>();

		window1.add(1.0);
		window1.add(1.0);
		window1.add(1.0);

		l.add(window1);

		List<List<Double>> mfs = mfb.applyMelFilterBank(l);
		Object[] result = { 0.007138359036312184, 0.009093836619532388 };
		for (List<Double> mf : mfs) {
			Assert.assertArrayEquals(result, mf.toArray());
		}
	}

	@Test
	public void testMelFilterBank10to300ThreeBanks() {
		mfb = new MelFilterBank(3, 0, 400);
		mfb.setSamplingRate(600);
		mfb.setFFTSize(3);

		List<List<Double>> l = new ArrayList<List<Double>>();
		List<Double> window1 = new ArrayList<Double>();

		window1.add(1.0);
		window1.add(1.0);
		window1.add(1.0);

		l.add(window1);

		List<List<Double>> mfs = mfb.applyMelFilterBank(l);
		Object[] result = { 0.0, 0.0017454143010668718, 0.009574830836899932 };

		for (List<Double> mf : mfs) {
			Assert.assertArrayEquals(result, mf.toArray());
		}
	}

	@Test
	public void testMelFilterBank10to300() {
		mfb = new MelFilterBank(2, 10, 300);
		mfb.setSamplingRate(600);
		mfb.setFFTSize(3);

		List<List<Double>> l = new ArrayList<List<Double>>();
		List<Double> window1 = new ArrayList<Double>();

		window1.add(1.0);
		window1.add(1.0);
		window1.add(1.0);

		l.add(window1);

		List<List<Double>> mfs = mfb.applyMelFilterBank(l);
		Object[] result = { 0.010510320137204567, 0.009502098976038039 };

		for (List<Double> mf : mfs) {
			Assert.assertArrayEquals(result, mf.toArray());
		}
	}
}
