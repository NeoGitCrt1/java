package com.company;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author ysy
 * @version v1.0
 * @description T0
 * @date 2019-02-27 14:48
 */
public class T0 {
    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        strings.add(null);
        Gson g = new Gson();
        System.out.println(g.toJson(strings));


        int c= 128 | 78;
        System.out.println(128 + 78);
        System.out.println(c);
        System.out.println(c & 128);
        System.out.println(c & 78);

        Random r = new Random();
        HashFunction h = Hashing.murmur3_128();
        long first = r.nextInt(2147483647) + System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            long s = r.nextInt(2147483647) + System.currentTimeMillis();

            System.out.println(s + ">>" + h.hashLong(s).asInt() + ">>" + (s - first));
        }
        BitSet bs = new BitSet();
        Gson gson = new Gson();
        Mesure.loop(50, (a) -> {
            gson.toJson(a);
            return null;
        }, "GSON", ImmutableMap.of("from", 900, "now", 600, "act", "V", "ts", 100000000));
        String toJson = gson.toJson(ImmutableMap.of("from", 900, "now", 600, "act", "V", "ts", 100000000));
        final Type DATA_TYPE = new TypeToken<Map<String, Object>>() {
        }.getType();
        Mesure.loop(50, (a) -> {
            gson.fromJson(a, DATA_TYPE);
            return null;
        }, "GSON DESERIALIZE", toJson);
        ViewEvent ve = new ViewEvent();
        ve.act = "V";
        ve.from = -657092492;
        ve.now = -592724235;
        ve.ts = 1551947112143L;
        Mesure.loop(500, (a) -> {
            gson.toJson(a);
            return null;
        }, "GSON ViewEvent", ve);
        String json = gson.toJson(ve);
        Mesure.loop(500, (a) -> {
            gson.fromJson(a, ViewEvent.class);
            return null;
        }, "GSON ViewEvent DESERIALIZE", json);

    }

    public static class ViewEvent {
        public int from;
        public int now;
        public String act;
        public String info;
        public long ts;

        @Override
        public String toString() {
            return String.format("%d - %s -> %d on %d", from, act, now, ts);
        }
    }
}
