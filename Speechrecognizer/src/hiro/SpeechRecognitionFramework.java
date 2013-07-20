package hiro;

import hiro.audio.AudioRecorder;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechRecognitionFramework implements ISpeechRecognition {

	static Logger LOG = LoggerFactory
			.getLogger(SpeechRecognitionFramework.class);

	public static void main(String[] args) throws IOException,
			InterruptedException {

		LOG.info("Hiro speech recognizer!! yay V0.1");

		ISpeechRecognition srfw = new SpeechRecognitionFramework();
		LOG.info(srfw.getTextFroMic());

	}

	public String getTextFroMic() {

		Preprocessor prepro = new Preprocessor();

		List<Integer> audio = getAudioData();
		List<List<Double>> mcep = prepro.getMcep(audio);

		// stack cepstrum or delta/delta-delta
		// cepstrum, -> HMM

		return "Kann noch nix erkennen";
	}

	private List<Integer> getAudioData() {
		AudioRecorder audioRecorder = new AudioRecorder();

		audioRecorder.init();
		audioRecorder.startRecording();
		LOG.info("Recording, press key to stop!");

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		audioRecorder.stopRecording();

		LOG.info("Stopped Recording with: "
				+ audioRecorder.getSoundData().size() + " samples");

		audioRecorder.tearDown();

		return audioRecorder.getSoundData();
	}

}