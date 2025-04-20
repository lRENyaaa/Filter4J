package filter.layer;

public class LeakyReLULayer extends Layer {

    private final int inputCount;

    public LeakyReLULayer(int inputCount) {
        this.inputCount = inputCount;
    }

    @Override
    public double[] forward(double[] input) {
        if (input.length != inputCount) {
            throw new RuntimeException("Wrong input size for LeakyRelu layer (expected " + inputCount + ", got " + input.length + ")");
        }
        for (int i = 0; i < inputCount; i++) {
            if (input[i] <= 0) {
                input[i] *= 0.01;
            }
        }
        return input;
    }
}
