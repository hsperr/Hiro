package hiro.window;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowFramer {

	static Logger LOG = LoggerFactory.getLogger(WindowFramer.class);

	public List<Double> getHammingWindow(int numSamples) {

		List<Double> hammingWindow = new ArrayList<Double>();
		final double alpha = 0.53836;
		final double beta = 0.46164;

		int denominator = numSamples - 1;
		double twoPI = Math.PI * 2;
		for (int i = 0; i < numSamples; i++) {
			hammingWindow.add(alpha - beta
					* Math.cos((twoPI * i) / denominator));
		}

		return hammingWindow;
	}

	/*
	 * Returns a List containing each windowFrame which is
	 * dataSampleSize/(samplesPerWindow-overlap) In each window frame the data
	 * is multiplied by the window
	 */
	public List<List<Double>> applyWindow(List<Integer> soundData,
			int samplesPerWindowFrame, int overlap) {
		List<List<Double>> result = new ArrayList<List<Double>>();
		List<Double> window = getHammingWindow(samplesPerWindowFrame);

		int numWindows = (int) Math.ceil(soundData.size()
				/ (samplesPerWindowFrame - overlap));

		for (int nw = 0; nw < numWindows; nw++) {
			result.add(new ArrayList<Double>());
			for (int i = 0; i < samplesPerWindowFrame; i++) {
				int dataIndex = nw * (samplesPerWindowFrame - overlap) + i;
				if (dataIndex >= soundData.size()) {
					result.get(nw).add(0.0);
				} else {
					result.get(nw)
							.add(soundData.get(dataIndex) * window.get(i));
				}
			}
		}

		return result;
	}
}
