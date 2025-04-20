package filter.layer;

public abstract class Layer {

    public abstract double[] forward(double[] input) throws JudgeResult;
}
