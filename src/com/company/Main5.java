package com.company;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ysy
 * @version v1.0
 * @description Main5
 * @date 2019-02-21 08:54
 */
public class Main5 {
    private static final Stopwatch sw = Stopwatch.createUnstarted();
    public static void main(String[] args) {
//        start(1, 6);
//        start(2, 6);
//        start(20, 6);
//        start(200, 6);
//        start(2000, 6);
//        start(20000, 6);
//        start(200000, 6);
//        start(2000000, 6);
//        Mesure.loop(2, s -> {
//            System.out.print("1.1.1_1.2.1>");
//            System.out.println(StringUtil.compareVersion("1.1.1", "1.2.1"));
//            return null;
//        }, "vs", "");

        Mesure.loop(5, s -> {
            System.out.print("1.10.1_1.10.10>");
            System.out.println(StringUtil.compareVersion2("1.10.1", "1.10.10"));
            return null;
        }, "vs2", "");
        Mesure.loop(5, s -> {
            System.out.print("1.9.1.1_1.10.10>");
            System.out.println(StringUtil.compareVersion2("1.9.1.1", "1.10.10"));
            return null;
        }, "vs3", "");
        Mesure.loop(5, s -> {
            System.out.print("1.9.1.1_1.9.1.10>");
            System.out.println(StringUtil.compareVersion2("1.9.1.1", "1.9.1.10"));
            return null;
        }, "vs4", "");
        Mesure.loop(5, s -> {
            System.out.print("1.9.10.1_1.9.1.10>");
            System.out.println(StringUtil.compareVersion2("1.9.10.1", "1.9.1.10"));
            return null;
        }, "vs5", "");
    }
    private static void start(final int size, final int loop) {
        List<Integer> raw = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            raw.add(i);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>list size:" + size);
        for (int i = 0; i < loop; i++) {
            run(raw);
            System.out.println("****************************");
        }
    }

    private static void run(final List<Integer> raw) {

        masure( in -> {
            List<String>  res = new ArrayList<>(raw.size());
            int size1 = res.size();
            for (int i = 0; i < size1; i++) {
                res.add(raw.get(i).toString());
            }
            res.forEach( one -> {

            });
            return res;
        }, "List -> ArrayList:", raw );

        masure( in -> {
            List<String> res = raw.stream().map(one -> one.toString()).collect(Collectors.toList());
            res.forEach( one -> {

            });
            return res;
        }, "List -> stream.map.collect:", raw );

        masure( in -> {
            List<String> res = Lists.transform(raw, one -> one.toString());
            res.forEach( one -> {

            });
            return res;
        }, "List -> .transform:", raw );


        masure( in -> {
            int rawsize = raw.size();
            String[] r2 = new String[rawsize];
            for (int i = 0; i < rawsize; i++) {
                r2[i] = raw.get(i).toString();
            }
            ImmutableList<String> res = ImmutableList.copyOf(r2);
            res.forEach( one -> {

            });
            return res;
        }, "List -> String[] -> ImmutableList.copyOf:", raw );

        masure( in -> {
            int rawsize2 = raw.size();
            String[] r3 = new String[rawsize2];
            for (int i = 0; i < rawsize2; i++) {
                r3[i] = raw.get(i).toString();
            }
            List<String> res = Arrays.asList(r3);
            res.forEach( one -> {

            });
            return res;
        }, "List -> String[] -> Arrays.asList:", raw );
    }

    private static  <U, T> long masure(Function<U, T> func, String info, U input) {
        sw.reset();
        sw.start();
        func.apply(input);
        long esp = sw.elapsed(TimeUnit.NANOSECONDS);
        System.out.print(esp);
        System.out.print("  <");
        System.out.print((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        System.out.print(">  ");
        System.out.println(info);
        return esp;
    }
}
