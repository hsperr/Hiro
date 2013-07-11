package hiro.audio;

import javax.sound.sampled.AudioFormat;

/**
 * Static class for information about the audio input
 * 
 * @author hsperr
 * 
 */
public class AudioInfo {

	public static final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_UNSIGNED;
	public static final float sampleRate = 16000.0F;
	public static final int sampleSizeInBits = 8;
	public static final int channels = 1;
	public static int frameSize = 1; // 1 byte per frame
	public static final int frameRate = 16000; // 8000
	public static final double windowSizeInSec = 30.0 / 1000.0; // 30ms windows
	public static final double overlapInSec = 20.0 / 1000.0;
	public static final boolean bigEndian = true;

	public static AudioFormat format = new AudioFormat(encoding, sampleRate,
			sampleSizeInBits, channels, frameSize, frameRate, bigEndian);

	public static int getSamplesPerWindowFrame() {
		return (int) Math.ceil(format.getSampleRate() * windowSizeInSec);
	}

	public static int getSamplesOverlap() {
		return (int) Math.ceil(format.getSampleRate() * overlapInSec);
	}

}
