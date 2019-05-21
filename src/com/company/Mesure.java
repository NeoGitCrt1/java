package com.company;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @author ysy
 * @version v1.0
 * @description Mesure
 * @date 2019-02-22 14:12
 */
public class Mesure {
    private static final Stopwatch sw = Stopwatch.createUnstarted();
    public static  <U, T> long masure(Function<U, T> func, String info, U input, boolean isShowLine) {
        sw.reset();
        sw.start();
        func.apply(input);
        long esp = sw.elapsed(TimeUnit.NANOSECONDS);
        if (isShowLine) {
            System.out.print(esp);
            System.out.print("  <");
            System.out.print((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            System.out.print(">  ");
            System.out.println(info);
        }

        return esp;
    }

    public static  <U, T> void loop(int loopCnt, Function<U, T> func, String info, U input) {
        long t = 0;
        int tc = loopCnt + 1;
        try {
            masure(func, info, input, false);
            for (int i = 1; i < tc; i++) {
                t += masure(func, info, input, false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(info + ">>>avg>>>>>>>>" + t / loopCnt);
    }
}
