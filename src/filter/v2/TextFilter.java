package filter.v2;

import filter.v2.resource.ResourceReader;

import java.io.FileInputStream;
import java.util.List;

public class TextFilter {

    private TextFilter() {
    }

    private static final List<MinRt.Layer> script;
    private static final Tokenizer tokenizer;

    static {
        try {
            script = ResourceReader.readResource(FileInputStream::new, "judge.model", reader -> MinRt.compile(reader.lines().toArray(String[]::new)));
            tokenizer = ResourceReader.readResource(FileInputStream::new, "tokenize.model", Tokenizer::loadFromReader);

        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static boolean isIllegal(String text) {
        return MinRt.doAi(tokenizer.tokenize(text), script) == 1;
    }

}
