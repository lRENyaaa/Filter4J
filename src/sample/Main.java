package sample;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filter4j 演示程序 已经启动!");
        while (true) {
            String str = scanner.nextLine();
            doV2(str);
            doV1(str);
            doV0(str);
        }
    }

    private static void doV2(String str) {
        long l1 = System.nanoTime();
        System.out.println("V2: " + (filter.v2.TextFilter.isIllegal(str) ? "异常" : "正常"));
        long l2 = System.nanoTime();
        System.out.println("V2过滤耗时: " + (l2 - l1) / 1000_000.0 + " ms");
    }

    private static void doV1(String str) {
        long l1 = System.nanoTime();
        System.out.println("V1: " + (filter.v1.TextFilter.isIllegal(str) ? "异常" : "正常"));
        long l2 = System.nanoTime();
        System.out.println("V1过滤耗时: " + (l2 - l1) / 1000_000.0 + " ms");
    }


    private static void doV0(String str) {
        long l1 = System.nanoTime();
        System.out.println("V0: " + (filter.v0.TextFilter.isIllegal(str) ? "异常" : "正常"));
        long l2 = System.nanoTime();
        System.out.println("V0过滤耗时: " + (l2 - l1) / 1000_000.0 + " ms");
    }
}
