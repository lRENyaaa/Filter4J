package filter;

import filter.layer.Layer;
import filter.resource.ResourceReader;

import java.io.FileInputStream;
import java.util.List;

public class TextFilter {
    private static final List<Layer> script;
    private static final Tokenizer tokenizer;

    static {
        try {
            script = ResourceReader.readResource(FileInputStream::new, "judge.model", reader -> ScriptCompiler.compile(reader.lines().toArray(String[]::new)));
            tokenizer = ResourceReader.readResource(FileInputStream::new, "tokenize.model", Tokenizer::loadFromReader);

        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public TextFilter() {
        throw new UnsupportedOperationException("This is a static class");
    }

    public static boolean isIllegal(String text) {
        return MinimalRuntime.doAi(tokenizer.tokenize(text), script) == 1;
    }
}
