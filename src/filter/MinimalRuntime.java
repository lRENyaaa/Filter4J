package filter;

import filter.layer.JudgeResult;
import filter.layer.Layer;

import java.util.List;

public class MinimalRuntime {
    private MinimalRuntime() {
    }

    public static int doAi(double[] input, List<Layer> compiledScript) {
        double[] current = new double[input.length];
        System.arraycopy(input, 0, current, 0, input.length);
        for (Layer layer : compiledScript) {
            try {
                current = layer.forward(current);
            } catch (JudgeResult result) {
                return result.getResult();
            }
        }

        throw new RuntimeException("No output layer");
    }
}
