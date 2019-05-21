package com.company;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class Main2 {

    public static void main(String[] args) {

        AB ab = new AB("aa");
        ab.post();

        System.out.println(ab.getAa());
        System.out.println(ab.getBb());
        System.out.println(ab.getClass().getName());
        Object abObj = ab;
        System.out.println(abObj.getClass().getName());



        System.out.println("*****************");
        String[] src = {"A", "B", "C"};
        String[] trg = new String[4];
        trg[0] = "a";

        System.arraycopy(src, 0 , trg, 1, src.length);

        System.out.println(ImmutableList.copyOf(trg));
        System.out.println("*****************");
        Type MAP_TYPE = new TypeToken<Map<String, String>>() {}.getType();

        Gson g1 = new Gson();
        String m1 = g1.toJson(ImmutableMap.of("NUM", 1, "STR", "AA"));
        System.out.println(m1);
        Map<String, String> r1 = g1.fromJson(m1, MAP_TYPE);
        System.out.println(g1.toJson(r1));
        System.out.println(g1.fromJson(m1, Map.class).toString());

        System.out.println("*****************");
        Gson g = new GsonBuilder().generateNonExecutableJson().create();
        Gson gg = new Gson();
        ImmutableMap<String, String> of = ImmutableMap.of("A", "AA", "B", "BB");
        System.out.println(g.toJson(of));
        System.out.println(gg.toJson(of));


        System.out.println("*****************");
        Map<String, String> m = new HashMap<>();
        System.out.println(m.get(null));
        System.out.println("Ture ^ True:" + (Boolean.TRUE ^ Boolean.TRUE));
        System.out.println("Ture ^ false:" + (Boolean.TRUE ^ Boolean.FALSE));
        System.out.println("false ^ Ture:" + (Boolean.FALSE ^ Boolean.TRUE));
        System.out.println("false ^ false:" + (Boolean.FALSE ^ Boolean.FALSE));
        System.out.println("*****************");
        System.out.println("!Ture ^ True:" + !(Boolean.TRUE ^ Boolean.TRUE));
        System.out.println("!Ture ^ false:" + !(Boolean.TRUE ^ Boolean.FALSE));
        System.out.println("!false ^ Ture:" + !(Boolean.FALSE ^ Boolean.TRUE));
        System.out.println("!false ^ false:" + !(Boolean.FALSE ^ Boolean.FALSE));
        System.out.println("*****************");
        System.out.println("Ture & True:" + (Boolean.TRUE & Boolean.TRUE));
        System.out.println("Ture & false:" + (Boolean.TRUE & Boolean.FALSE));
        System.out.println("false & Ture:" + (Boolean.FALSE & Boolean.TRUE));
        System.out.println("false & false:" + (Boolean.FALSE & Boolean.FALSE));
        System.out.println("*****************");
        System.out.println("Ture | True:" + (Boolean.TRUE | Boolean.TRUE));
        System.out.println("Ture | false:" + (Boolean.TRUE | Boolean.FALSE));
        System.out.println("false | Ture:" + (Boolean.FALSE | Boolean.TRUE));
        System.out.println("false | false:" + (Boolean.FALSE | Boolean.FALSE));
        System.out.println("*****************");
        System.out.println(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println("*****************");
        LocalDate expireDate = LocalDate.of(2018, 1, 1);
        LocalDate openDate = LocalDate.of(2018, 1, 1);

        // 过期逻辑
        boolean isNotify = needNotify(expireDate);

        // 新开逻辑
        isNotify = needNotify(openDate.plusDays(1));
    }

    private static boolean needNotify(LocalDate expireDate) {
        LocalDate now = LocalDate.now();
        if (expireDate.equals(now)) {
            return true;
        }
        if (expireDate.plusDays(1).equals(now)) {
            return true;
        }
        if (expireDate.plusDays(15).equals(now)) {
            return true;
        }
        return false;
    }

    private static class AB {
        private String aa;
        private String bb;

        public String getBb() {
            return bb;
        }

        public String getAa() {
            return aa;
        }

        public AB(String aa) {
            this.aa = aa;
            this.bb = null;
        }

        public void post() {
            if (this.aa != null) {
                bb = aa;
                aa = null;
            }
        }

    }
}
