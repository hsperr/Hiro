package hiro.FFT;

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class FastFurierTransformer {

	public List<List<Double>> transformWindowList(
			List<List<Double>> windowedSignal) {
		List<List<Double>> transformedWindows = new ArrayList<List<Double>>();

		int fftSize = (int) Math
				.pow(2,
						Math.ceil(Math.log(windowedSignal.get(0).size())
								/ Math.log(2)));

		for (List<Double> window : windowedSignal) {
			transformedWindows.add(new ArrayList<Double>());
			DoubleFFT_1D fftTransform = new DoubleFFT_1D(fftSize);
			double[] result = new double[fftSize];

			for (int i = 0; i < fftSize; i++) {
				if (i < window.size()) {
					result[i] = window.get(i);
				} else {
					result[i] = 0;
				}
			}

			fftTransform.realForward(result);

			for (int i = 0; i < fftSize; i++) {
				transformedWindows.get(transformedWindows.size() - 1).add(
						result[i]);
			}

		}
		return transformedWindows;
	}
}
