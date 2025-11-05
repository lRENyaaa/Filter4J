package filter.v2;

import filter.v2.algorithm.AhoCorasickDoubleArrayTrie;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.DoubleStream;

public class Tokenizer {
    private final AhoCorasickDoubleArrayTrie<Integer> acdat;

    public Tokenizer(String[] vocab) {

        TreeMap<String, Integer> map = new TreeMap<>();

        for (int i = 0, vocabLength = vocab.length; i < vocabLength; i++) {
            String key = vocab[i];
            map.put(key, i);
        }
        // Build an AhoCorasickDoubleArrayTrie
        acdat = new AhoCorasickDoubleArrayTrie<>();
        acdat.build(map);
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
        List<AhoCorasickDoubleArrayTrie.Hit<Integer>> hits = acdat.parseText(text);
        return DoubleStream.concat(DoubleStream.of(Double.NaN), hits.stream().mapToDouble(v -> (double) v.value)).toArray();
    }
}
