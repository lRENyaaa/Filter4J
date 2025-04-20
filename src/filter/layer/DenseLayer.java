package filter.layer;

public class DenseLayer extends Layer {

    private final int inputCount;
    private final int outputCount;
    private final double[][] weights;

    public DenseLayer(int inputCount, int outputCount, double[][] weights) {
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        this.weights = weights;
    }

    @Override
    public double[] forward(double[] input) {
        if (input.length != inputCount) {
            throw new RuntimeException("Wrong input size for Dense layer (expected " + inputCount + ", got " + input.length + ")");
        }
        double[] output = new double[outputCount];
        for (int i = 0; i < outputCount; i++) {
            double sum = 0;
            for (int j = 0; j < inputCount; j++) {
                sum += input[j] * weights[j][i];
            }
            output[i] = sum;
        }
        return output;
    }
}
