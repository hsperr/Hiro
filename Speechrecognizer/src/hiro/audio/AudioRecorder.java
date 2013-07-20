package hiro.audio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class gives basic functionality to record audio from a built in device.
 * Needs to be initialized before usage.
 * 
 * @author hsperr
 * 
 */
public class AudioRecorder {

	static Logger LOG = LoggerFactory.getLogger(AudioRecorder.class);

	private TargetDataLine line = null;
	private DataLine.Info info = null;
	private AudioInputStream stream = null;
	private List<Integer> soundData = new ArrayList<Integer>();
	private AudioStreamReader recorder = null;
	private Thread testThread = null;
	private AudioSettings settings = null;

	public AudioRecorder(AudioSettings settings) {
		this.settings = settings;
	}

	public void init() {
		info = new DataLine.Info(TargetDataLine.class, settings.getFormat());

		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(settings.getFormat());
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		line.start();

		stream = new AudioInputStream(line);

		recorder = new AudioStreamReader(soundData, stream);

		testThread = new Thread(recorder);
		testThread.start();
	}

	public void startRecording() {
		recorder.startRecording();
	}

	public void startNewRecording() {
		clearData();
		startRecording();
	}

	public void clearData() {
		this.soundData.clear();
	}

	public void stopRecording() {
		recorder.stopRecording();
	}

	public void saveToFile(String filename) {
		File file = new File(filename);
		if (settings.getFrameSize() > 1) {
			LOG.error("AudioRecorder: Error, audio doesn't support frames > 1 byte right now");
			return;
		}
		try {
			// TODO: Seems a bit dirty but we need a bytestream
			ByteBuffer buffer = ByteBuffer.allocate(soundData.size());
			byte[] sd = new byte[soundData.size()];
			for (int i = 0; i < soundData.size(); i++) {
				int sound = soundData.get(i);
				sd[i] = (byte) sound;
			}
			buffer.put(sd);
			InputStream b = new ByteArrayInputStream(buffer.array());
			AudioInputStream a = new AudioInputStream(b, settings.getFormat(),
					soundData.size());
			AudioSystem.write(a, AudioFileFormat.Type.WAVE, file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFromFile(String filename) {
		LOG.error("Not Implemented Yet");
	}

	public void tearDown() {
		recorder.stopRunning();
	}

	public List<Integer> getSoundData() {
		return soundData;
	}

	public List<Integer> recordFromMicrophone() {
		this.init();
		this.startRecording();
		LOG.info("Recording, press key to stop!");

		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.stopRecording();

		LOG.info("Stopped Recording with: " + this.getSoundData().size()
				+ " samples");

		// TODO maybe do not tear down after one use
		this.tearDown();

		return getSoundData();

	}
}

class AudioStreamReader implements Runnable {
	private boolean running = true;
	private boolean recording = false;
	private AudioInputStream stream;
	private List<Integer> list;

	AudioStreamReader(List<Integer> list, AudioInputStream stream) {
		this.stream = stream;
		this.list = list;
	}

	public void run() {
		int readData;
		while (running) {
			if (!recording)
				continue;

			try {
				readData = stream.read();
				if (readData > 0) {
					list.add(readData);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void startRecording() {
		recording = true;
	}

	public void stopRecording() {
		recording = false;
	}

	public void startRunning() {
		running = true;
	}

	public void stopRunning() {
		running = false;
	}
}
