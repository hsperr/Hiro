package hiro;

import hiro.FFT.FastFurierTransformer;
import hiro.audio.AudioSettings;
import hiro.filter.MelFilterBank;
import hiro.window.WindowFramer;

import java.util.List;

public class Preprocessor {
	private WindowFramer windowFramer = null;
	private FastFurierTransformer fftTransformer = null;
	private MelFilterBank melBank = null;
	private AudioSettings settings = null;

	public Preprocessor(AudioSettings settings) {
		this.settings = settings;
		windowFramer = new WindowFramer();
		fftTransformer = new FastFurierTransformer();
		melBank = new MelFilterBank();
	}

	public List<List<Double>> getMCEP(List<Integer> soundData) {

		List<List<Double>> windowedSignal = windowFramer.applyWindow(soundData,
				settings.getSamplesPerWindowFrame(),
				settings.getSamplesOverlap());

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
