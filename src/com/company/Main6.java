package com.company;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author ysy
 * @version v1.0
 * @description Main6
 * @date 2019-02-23 11:01
 */
public class Main6 {
    public static void main(String[] args) {
        Random r = new Random();
        List<Integer> raw = new ArrayList<>(1000000);
        for (int i = 0; i < raw.size(); i++) {
            raw.add(r.nextInt());
        }
        Iterable<Integer> iterable = raw;


        Mesure.loop(5, s -> {
            List<Integer> allItems = ImmutableList.copyOf(iterable);
            allItems.sort(Comparator.naturalOrder());
            return null;
        }, "ImmutableList.copyOf", "");
        Mesure.loop(5, s -> {
            List<Integer> allItems = new ArrayList<>();
            for (Integer item : iterable) {
                allItems.add(item);
            }
            allItems.sort(Comparator.naturalOrder());
            return null;
        }, "ArrayList add", "");
        Mesure.loop(5, s -> {
            List<Integer> allItems = new LinkedList<>();
            for (Integer item : iterable) {
                allItems.add(item);
            }
            allItems.sort(Comparator.naturalOrder());
            return null;
        }, "LinkedList add", "");
    }
}
