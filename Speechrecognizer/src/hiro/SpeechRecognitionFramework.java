package hiro;

import hiro.FFT.FastFurierTransformer;
import hiro.audio.AudioInfo;
import hiro.audio.AudioRecorder;
import hiro.filter.MelFilterBank;
import hiro.window.WindowFramer;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechRecognitionFramework implements ISpeechRecognition {

	static final Logger logger = LoggerFactory
			.getLogger(SpeechRecognitionFramework.class);

	public static void main(String[] args) throws IOException,
			InterruptedException {

		logger.info("Hiro speech recognizer!! yay V0.1");

		ISpeechRecognition srfw = new SpeechRecognitionFramework();
		logger.info(srfw.getTextFroMic());

	}

	public String getTextFroMic() {

		AudioRecorder audioRecorder = new AudioRecorder();
		WindowFramer windowFramer = new WindowFramer();
		FastFurierTransformer fftTransformer = new FastFurierTransformer();
		MelFilterBank melBank = new MelFilterBank();

		audioRecorder.init();
		audioRecorder.startRecording();
		logger.info("Recording, press key to stop!");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		audioRecorder.stopRecording();

		logger.info("Stopped Recording with: "
				+ audioRecorder.getSoundData().size() + " samples");

		List<List<Double>> windowedSignal = windowFramer.applyWindow(
				audioRecorder.getSoundData(),
				AudioInfo.getSamplesPerWindowFrame(),
				AudioInfo.getSamplesOverlap());

		// furier transfor every List<Double> in windowedSignal
		List<List<Double>> realPowerSpectrum = fftTransformer
				.transformWindowList(windowedSignal);

		// mel filter banks
		// melFrequency = 2595 * log(1 + linearFrequency/700)

		// inverse furier

		// cepstrum, -> HMM

		audioRecorder.tearDown();

		return "Kann noch nix erkennen";
	}

}