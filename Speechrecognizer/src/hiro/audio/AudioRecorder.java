package hiro.audio;

import hiro.audio.AudioInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * This class gives basic functionality to record audio from a built in device.
 * Needs to be initialized before usage.
 * 
 * @author hsperr
 * 
 */
public class AudioRecorder {

	private TargetDataLine line = null;
	private DataLine.Info info = null;
	private AudioInputStream stream = null;
	private List<Integer> soundData = new ArrayList<Integer>();
	private AudioStreamReader recorder = null;
	private Thread testThread = null;

	public void init() {
		info = new DataLine.Info(TargetDataLine.class, AudioInfo.format);

		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(AudioInfo.format);
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
		try {
			AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFromFile(String filename) {
		System.out.println("Not Implemented Yet");
	}

	public void tearDown() {
		recorder.stopRunning();
	}

	public List<Integer> getSoundData() {
		return soundData;
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
