package com.company;

import javax.sound.midi.Soundbank;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main3 {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("@@@@@@@@@@@@@@@@@@@@@@@@@");
        sb.setLength(2);
        sb.append("####");
        System.out.println(sb.toString());

        Long sss = 1L;
        Object a = sss;
        double sa = (Long) a;
        System.out.println(sa);

        System.out.println(        Integer.MAX_VALUE
        );
        System.out.println("4188896463");

        List<String> aaa = new ArrayList<>(5);
        for (int i = 0; i < 2; i++) {
            aaa.add(String.valueOf(i));
        }
        for (int i = 0; i < 20; i++) {
            Collections.shuffle(aaa);
            System.out.println(aaa);
        }
        Collections.shuffle(aaa);
        System.out.println(aaa);
        System.out.println("############");
        for (int i = 0; i < 200; i++) {
            aaa.add(String.valueOf(i));
        }
        DoubleStream astream = aaa.parallelStream().mapToDouble(one -> {
            System.out.println(">" + one);
            return Double.valueOf(one) / 100;
        }).filter( i -> {
            System.out.println("}" + i);
            return i > 0.1;
        });
        System.out.println("{}{}{}{}{}{}");
        System.out.println("average: " + astream.average().orElse(0d));

    }

}
