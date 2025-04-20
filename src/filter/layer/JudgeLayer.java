package filter.layer;

public class JudgeLayer extends Layer {

    private final int inputCount;

    public JudgeLayer(int inputCount) {
        this.inputCount = inputCount;
    }

    @Override
    public double[] forward(double[] input) throws JudgeResult {
        if (input.length != inputCount) {
            throw new RuntimeException("Wrong input size for Judge layer (expected " + inputCount + ", got " + input.length + ")");
        }
        int index = 0;
        for (int i = 1; i < inputCount; i++) {
            if (input[i] > input[index]) {
                index = i;
            }
        }
        throw new JudgeResult(index);
    }
}
