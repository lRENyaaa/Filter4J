package filter;

import java.io.BufferedReader;
import java.io.IOException;

public class Tokenizer {
    private final String[] vocab;

    public Tokenizer(String[] vocab) {
        this.vocab = vocab;
    }

    public static Tokenizer loadFromReader(BufferedReader reader) throws IOException {
        int size = Integer.parseInt(reader.readLine());
        String[] vocab = new String[size];
        for (int i = 0; i < size; i++) {
            vocab[i] = reader.readLine();
        }
        return new Tokenizer(vocab);
    }

    public double[] tokenize(String text) {
        double[] values = new double[vocab.length];
        for (int i = 0; i < values.length; i++) {
            if (text.contains(vocab[i])) {
                values[i] = 1;
            }
        }
        return values;
    }
}
