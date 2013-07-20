package hiro.FFT;

import hiro.FFT.FastFurierTransformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FastFurierTransformerTest {

	static FastFurierTransformer fft = null;

	@BeforeClass
	public static void testSetup() {
		fft = new FastFurierTransformer();

	}

	@AfterClass
	public static void testCleanup() {
		fft = null;
	}

	@Test
	public void testRealPowerSpectrum() {
		// Result calculated with wolfram alpha
		List<Double> fakeAudio = new ArrayList<Double>();

		for (int i = 0; i < 8; i++)
			fakeAudio.add((double) i);
		List<Double> fftData = fft.calculatePowerSpectrum(fakeAudio);

		Object[] result = { 28.0, 10.452503719011013, 5.656854249492381,
				4.329568801169576, 4.0, 4.329568801169576, 5.656854249492381,
				10.452503719011013 };

		Assert.assertArrayEquals(result, fftData.toArray());
	}

	@Test
	public void testRealPowerSpectrumFromWindowList() {
		// Result calculated with wolfram alpha
		List<List<Double>> windowList = new ArrayList<List<Double>>();
		List<Double> fakeAudio = new ArrayList<Double>();

		for (int i = 0; i < 8; i++)
			fakeAudio.add((double) i);

		windowList.add(fakeAudio);
		windowList.add(fakeAudio);

		List<List<Double>> fftData = fft
				.calculatePowerSpectrumOfWindowList(windowList);

		Object[] result = { 28.0, 10.452503719011013, 5.656854249492381,
				4.329568801169576, 4.0, 4.329568801169576, 5.656854249492381,
				10.452503719011013 };

		for (List<Double> l : fftData)
			Assert.assertArrayEquals(result, l.toArray());
	}
}
