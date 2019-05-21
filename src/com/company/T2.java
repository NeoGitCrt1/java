package com.company;

import com.company.model.CloneTest;
import com.company.model.CloneTest2;
import com.company.model.CloneTest3;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ysy
 * @version v1.0
 * @description T2
 * @date 2019-03-25 10:05
 */
public class T2 {
    public static void main(String[] a) {
        String name = "666666666666666666dsadsadsadsadsadsa";
        long ts = System.currentTimeMillis();
        int loop = 100000000;
        Mesure.loop(loop, s -> {
            return new CloneTest(s, ts);
        }, "CloneTest", name);

        CloneTest2 ct2 = new CloneTest2();
        ct2.uu = name;
        ct2.ts = ts;

        Mesure.loop(loop, s -> {
            return s.clone();
        }, "CloneTest2", ct2);

        Mesure.loop(loop, s -> {
            CloneTest2 ct22 = new CloneTest2();
            ct22.uu = name;
            ct22.ts = ts;
            return ct22;
        }, "CloneTest22", ct2);

    }
}
