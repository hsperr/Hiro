package hiro;

import hiro.audio.AudioRecorder;
import hiro.audio.AudioSettings;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechRecognitionFramework implements ISpeechRecognition {

	static Logger LOG = LoggerFactory
			.getLogger(SpeechRecognitionFramework.class);

	public static void main(String[] args) throws IOException,
			InterruptedException {

		LOG.info("Hiro speech recognizer!! yay V0.11");

		ISpeechRecognition srfw = new SpeechRecognitionFramework();
		LOG.info(srfw.getTextFroMic());

	}

	public String getTextFroMic() {

		AudioSettings settings = AudioSettings.MICROPHONE_SETTINGS;
		Preprocessor prepro = new Preprocessor(settings);
		AudioRecorder recorder = new AudioRecorder(settings);

		List<Integer> audioData = recorder.recordFromMicrophone();
		List<List<Double>> mcep = prepro.getMCEP(audioData);

		// stack cepstrum or delta/delta-delta
		// cepstrum, -> HMM

		return "Kann noch nix erkennen";
	}

}