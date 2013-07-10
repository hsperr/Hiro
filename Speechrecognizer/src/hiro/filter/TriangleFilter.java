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
				.round((this.rightEdge - this.leftEdge) / (this.deltaF + 1));

		this.weights = new ArrayList<Double>(this.numberOfWeights);

		double rightSlope = -this.height / (rightEdge - center);
		double leftSlope = this.height / (center - leftEdge);

		double leftb = -leftEdge * leftSlope;
		double rightb = -rightEdge * rightSlope;

		// y=mx+b
		while (initF < leftEdge) {
			initF += this.deltaF;
		}
		for (int i = 0; i < this.numberOfWeights; i++) {
			double currentF = this.initF + i * this.deltaF;
			double weight = 0.0;
			if (currentF < this.center) {
				weight = leftSlope * currentF + leftb;
			} else {
				weight = rightSlope * currentF + rightb;
			}
			this.weights.add(weight);
		}

		// System.out.println(weights);
	}

	@Override
	public double applyFilter(List<Double> values) {
		assert (values.size() == weights.size());
		double output = 0;
		int firstValueIndex = (int) ((int) this.initF / this.deltaF) + 1;

		for (int i = 0; i < weights.size(); i++) {
			output += values.get(firstValueIndex + i) * weights.get(i);

		}
		return output;
	}
}
