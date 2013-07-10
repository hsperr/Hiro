package hiro.filter;

import java.util.ArrayList;
import java.util.List;

public class TriangleFilter implements Filter {

	private double height = 2;
	private double leftEdge = 0;
	private double rightEdge = 0;
	private double center = 0;
	private double initF = 0;
	private double deltaF = 0;
	private List<Double> weights = null;
	private int numberOfWeights;

	public TriangleFilter(double leftEdge, double center, double rightEdge,
			double initFreq, double deltaF) {
		this.leftEdge = leftEdge;
		this.center = center;
		this.rightEdge = rightEdge;
		this.initF = initFreq;
		this.deltaF = deltaF;

		this.height = 2.0 / (this.rightEdge - this.leftEdge);
		this.numberOfWeights = (int) Math
				.round((this.rightEdge - this.leftEdge) / this.deltaF + 1);
		this.weights = new ArrayList<Double>(this.numberOfWeights);

	}

	@Override
	public void applyFilter(List<Double> values) {
		// TODO Auto-generated method stub

	}

}
