package hiro.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AudioRecorderTest {

	static AudioRecorder recorder = null;

	@BeforeClass
	public static void testSetup() {
		recorder = new AudioRecorder();
		recorder.init();
	}

	@AfterClass
	public static void testCleanup() {
		recorder.tearDown();
		recorder = null;
	}

	public void recordData(int seconds) {
		recorder.startNewRecording();
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.stopRecording();
	}

	@Test
	public void testEmptyData() {
		assertEquals("No Sound recorded, should be empty", 0, recorder
				.getSoundData().size());
	}

	@Test
	public void testDataRecording() {
		recordData(1);
		assertTrue("Should not be empty since data was recorded", 0 < recorder
				.getSoundData().size());
	}

	@Test
	public void testNewDataRecording() {
		recordData(1);
		int currentSize = recorder.getSoundData().size();

		recorder.startNewRecording();
		recorder.stopRecording();

		assertFalse("Should not be same", currentSize == recorder
				.getSoundData().size());

	}

}
