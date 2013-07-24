package hiro.audio;

import javax.sound.sampled.AudioFormat;

/**
 * Static class for information about the audio input
 * 
 * @author hsperr
 * 
 */
public enum AudioSettings {

	MICROPHONE_SETTINGS("Microphone_Settings",
			AudioFormat.Encoding.PCM_UNSIGNED, 16000, 8, 1, 1, 16000, 0.03,
			0.02, true);

	private final AudioFormat.Encoding encoding;
	private final String description;
	private final int sampleRate;
	private final int sampleSizeInBits;
	private final int channels;
	private final int frameSize;
	private final int frameRate;
	private final double windowSizeInSec;
	private final double overlapInSec;
	private final boolean bigEndian;

	private final AudioFormat format;

	private AudioSettings(String description, AudioFormat.Encoding encoding,
			int sampleRate, int sampleSizeInBits, int channels, int frameSize,
			int frameRate, double windowSizeInSec, double overlapInSec,
			boolean bigEndian) {
		this.description = description;
		this.encoding = encoding;
		this.sampleRate = sampleRate;
		this.sampleSizeInBits = sampleSizeInBits;
		this.channels = channels;
		this.frameSize = frameSize; // 1 byte per frame
		this.frameRate = frameRate; // 8000
		this.windowSizeInSec = windowSizeInSec; // 30ms windows
		this.overlapInSec = overlapInSec;
		this.bigEndian = true;
		this.format = new AudioFormat(encoding, sampleRate, sampleSizeInBits,
				channels, frameSize, frameRate, bigEndian);
	}

	public int getSamplesPerWindowFrame() {
		return (int) Math.ceil(format.getSampleRate() * windowSizeInSec);
	}

	public AudioFormat.Encoding getEncoding() {
		return encoding;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}

	public int getChannels() {
		return channels;
	}

	public int getFrameSize() {
		return frameSize;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public double getWindowSizeInSec() {
		return windowSizeInSec;
	}

	public double getOverlapInSec() {
		return overlapInSec;
	}

	public boolean isBigEndian() {
		return bigEndian;
	}

	public AudioFormat getFormat() {
		return format;
	}

	@Override
	public String toString() {
		return description;
	}

	public int getSamplesOverlap() {
		return (int) Math.ceil(format.getSampleRate() * overlapInSec);
	}

}
