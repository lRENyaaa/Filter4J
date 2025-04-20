package filter.layer;

public class JudgeResult extends Exception {

    private final int result;

    public JudgeResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

}
