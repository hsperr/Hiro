package hiro;

import hiro.FFT.FastFurierTransformer;
import hiro.audio.AudioInfo;
import hiro.filter.MelFilterBank;
import hiro.window.WindowFramer;

import java.util.List;

public class Preprocessor {

	public List<List<Double>> getMcep(List<Integer> soundData) {
		WindowFramer windowFramer = new WindowFramer();
		FastFurierTransformer fftTransformer = new FastFurierTransformer();
		MelFilterBank melBank = new MelFilterBank();

		List<List<Double>> windowedSignal = windowFramer.applyWindow(soundData,
				AudioInfo.getSamplesPerWindowFrame(),
				AudioInfo.getSamplesOverlap());

		// furier transfor every List<Double> in windowedSignal
		List<List<Double>> realPowerSpectrum = fftTransformer
				.calculatePowerSpectrumOfWindowList(windowedSignal);

		// mel filter banks
		// melFrequency = 2595 * log(1 + linearFrequency/700)
		List<List<Double>> melFilteredSignal = melBank
				.applyMelFilterBank(realPowerSpectrum);

		// inverse furier
		List<List<Double>> cepstralCoefficients = fftTransformer.calculateDCT(
				melFilteredSignal, true);

		return cepstralCoefficients;

	}

}
