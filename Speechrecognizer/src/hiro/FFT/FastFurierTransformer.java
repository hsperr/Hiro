package hiro.FFT;

import hiro.audio.AudioInfo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_1D;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * FFT uses JTransform for the actual transformation.
 * 
 * @author hsperr
 * 
 */
public class FastFurierTransformer {

	static Logger LOG = LoggerFactory.getLogger(FastFurierTransformer.class);

	public List<List<Double>> calculatePowerSpectrumOfWindowList(
			List<List<Double>> windowedSignal) {
		List<List<Double>> transformedWindows = new ArrayList<List<Double>>();

		for (List<Double> window : windowedSignal) {
			List<Double> powerSpectrum = calculatePowerSpectrum(window);
			transformedWindows.add(powerSpectrum);
		}
		return transformedWindows;
	}

	public List<Double> calculatePowerSpectrum(List<Double> data) {
		List<Double> result = new ArrayList<Double>();

		int fftSize = getRealFFTSize(data.size());

		DoubleFFT_1D fftTransform = new DoubleFFT_1D(fftSize);
		double[] fftData = prepareDataArray(data, fftSize, true);

		fftTransform.complexForward(fftData);

		for (int i = 0; i < fftSize; i++) {
			// calculate powerspectrum as sqrt(re*re+im*im)
			result.add(Math.sqrt(fftData[2 * i] * fftData[2 * i]
					+ fftData[2 * i + 1] * fftData[2 * i + 1]));
		}

		return result;
	}

	public double getPeakFrequencyOfPowerSpectrum(List<Double> powerSpectrum) {
		double maxMag = Double.MIN_VALUE;
		int maxInd = -1;

		for (int i = 0; i < powerSpectrum.size(); i++) {
			if (powerSpectrum.get(i) > maxMag) {
				maxMag = powerSpectrum.get(i);
				maxInd = i;
			}
		}

		return 1.0 * maxInd * AudioInfo.sampleRate / powerSpectrum.size();
	}

	private int getRealFFTSize(int dataSize) {
		// needs to be smallest power of two>dataSize
		return (int) Math.pow(2, Math.ceil(Math.log(dataSize) / Math.log(2)));
	}

	private double[] prepareDataArray(List<Double> data, int fftSize,
			boolean complex) {
		int mul = 1; // multiplier for complex numbers
		int add = 0; // shift for complex numbers
		if (complex) {
			mul = 2;
			add = 1;
		}

		double[] result = new double[mul * fftSize];

		// array contains [real1,complex1,real2,complex2,...,realn,complexn]
		// array gets padded with zero if datasize<fftsize
		// if complex = false
		for (int i = 0; i < fftSize; i++) {
			if (i < data.size()) {
				result[mul * i + add] = 0;
				result[mul * i] = data.get(i);

			} else {
				result[mul * i + add] = 0;
				result[mul * i] = 0;
			}
		}
		return result;
	}

	public List<List<Double>> calculateDCT(List<List<Double>> windowedSignal,
			boolean inverse) {

		List<List<Double>> transformedWindows = new ArrayList<List<Double>>();

		for (List<Double> window : windowedSignal) {
			List<Double> result = new ArrayList<Double>();

			int dctSize = window.size();

			DoubleDCT_1D dctTransform = new DoubleDCT_1D(dctSize);
			double[] dctData = prepareDataArray(window, dctSize, true);

			if (inverse) {
				dctTransform.inverse(dctData, false);
			} else {
				dctTransform.forward(dctData, false);
			}

			for (int i = 0; i < dctSize; i++) {
				// calculate powerspectrum as sqrt(re*re+im*im)
				result.add(dctData[i]);
			}

			transformedWindows.add(result);
		}
		return transformedWindows;

	}
}
