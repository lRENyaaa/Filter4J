package filter.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MinRt {
    private MinRt() {
    }

    private static final Map<String, Function<String[], Layer>> compilers = createCompilers();

    private static Map<String, Function<String[], Layer>> createCompilers() {
        Map<String, Function<String[], Layer>> map = new HashMap<>();

        map.put("D", (tokens) -> {
            int inputCount = Integer.parseInt(tokens[1]);
            int outputCount = Integer.parseInt(tokens[2]);
            double[][] weights = new double[inputCount][outputCount];
            for (int j = 0; j < inputCount; j++) {
                for (int i = 0; i < outputCount; i++) {
                    weights[j][i] = Double.parseDouble(tokens[3 + i + j * outputCount]);
                }
            }
            return new DenseLayer(inputCount, outputCount, weights);
        });

        map.put("L", (tokens) -> new LeakyReLULayer(Integer.parseInt(tokens[1])));
        map.put("J", (tokens) -> new JudgeLayer(Integer.parseInt(tokens[1])));

        return Collections.unmodifiableMap(map);
    }

    public static abstract class Layer {

        public abstract double[] forward(double[] input) throws JudgeResult;
    }

    private static class DenseLayer extends Layer {

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
            if (Double.isNaN(input[0])) {
                return initForward(input);
            }

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

        private double[] initForward(double[] input) {
            double[] output = new double[outputCount];
            for (int i = 0; i < outputCount; i++) {
                double sum = 0;
                for (double v : input) {
                    if (Double.isNaN(v)) {
                        continue;
                    }

                    if (v > inputCount) {
                        throw new RuntimeException("Wrong input size for Dense layer (expected " + inputCount + ", got " + input.length + ")");
                    }
                    sum += weights[(int) v][i];
                }
                output[i] = sum;
            }
            return output;
        }
    }

    private static class JudgeLayer extends Layer {

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

    private static class LeakyReLULayer extends Layer {

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

    public static class JudgeResult extends Exception {

        private final int result;

        public JudgeResult(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }

    }

    public static List<Layer> compile(String[] script) {
        List<Layer> layers = new ArrayList<>();
        for (String string : script) {
            if (string.length() < 2) {
                continue;
            }

            String[] tokens = string.split(" ");
            Function<String[], Layer> compiler = compilers.get(tokens[0]);
            if (compiler == null) {
                throw new RuntimeException("Unknown layer type: " + tokens[0]);
            }
            layers.add(compiler.apply(tokens));
        }

        return layers;
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


    @Deprecated
    public static int doAi(double[] input, String[] script) {
        return doAi(input, compile(script));
    }
}
