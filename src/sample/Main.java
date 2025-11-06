package sample;

import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filter4j 演示程序 已经启动!");
        while (true) {
            String message = scanner.nextLine();
            filter(filter.v2.TextFilter::isIllegal, message, "V2");
            filter(filter.v1.TextFilter::isIllegal, message, "V1");
            filter(filter.v0.TextFilter::isIllegal, message, "V0");
        }
    }

    private static void filter(Function<String,Boolean> checker, String message, String version) {
        long l1 = System.nanoTime();
        Boolean result = checker.apply(message);
        long l2 = System.nanoTime();
        System.out.println(version + ": " + (result ? "异常" : "正常"));
        System.out.println(version + "过滤耗时: " + (l2 - l1) / 1000_000.0 + " ms");
    }
}
