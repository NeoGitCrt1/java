package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ysy
 * @version v1.0
 * @description Main4
 * @date 2019-02-18 10:06
 */
public class Main4 {
    public static void main(String[] args) {
        Map<String, String> m = new HashMap<>();
        m.put("A", "A");

        Map<String, String> m2 = new HashMap<>();
        m2.put("A", "AA");
        m2.put("AB", "AABB");

        m.putAll(m2);

        System.out.println(m);

        List<String> vl = new ArrayList<>();
        vl.add("ios@1.0.0");
        vl.add("ios@1.0.8");
        vl.add("androId@1.0.0");
        vl.add("androId@1.0.6");

        vl.stream()

                .sorted((v1, v2) -> {

            int v1Pos = v1.indexOf("@");
            int v2Pos = v2.indexOf("@");

            int sys = v1.substring(0, v1Pos).compareTo(v2.substring(0, v2Pos));
            if (sys != 0) {
                return sys;
            }
                    return StringUtil.compareVersion(v2.substring(v2Pos + 1), v1.substring(v2Pos + 1));
                }).forEach(
                        s -> System.out.println(s)
        );
    }
}
