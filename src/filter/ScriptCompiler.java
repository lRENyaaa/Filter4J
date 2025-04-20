package filter;

import filter.layer.DenseLayer;
import filter.layer.JudgeLayer;
import filter.layer.Layer;
import filter.layer.LeakyReLULayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ScriptCompiler {

    private static final Map<String, Function<String[], Layer>> compilers;

    static {
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

        compilers = Collections.unmodifiableMap(map);
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

}
