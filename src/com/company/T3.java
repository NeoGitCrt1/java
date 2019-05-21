package com.company;

import com.google.common.hash.Hashing;

import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author ysy
 * @version v1.0
 * @description T3
 * @date 2019-04-15 15:33
 */
public class T3 {
    public static void main(String[] aa) {
        int page = 0;
        int pageSize = 10;
        for (page = 0; page < 10; page++) {
            System.out.println(String.format("%d>%d",page * pageSize , pageSize + pageSize * page - 1));
        }


        String d0 = new StringJoiner("_").add("1001").add("11").add("0").toString();
        String d1 = new StringJoiner("_").add("1001").add("11").add("1").toString();
        String d2 = new StringJoiner("_").add("1001").add("11").add("2").toString();
        String d3 = new StringJoiner("_").add("1001").add("11").add("3").toString();
        ;
        System.out.println(UUID.nameUUIDFromBytes(d0.getBytes()).toString());
        System.out.println(UUID.nameUUIDFromBytes(d1.getBytes()).toString());
        System.out.println(UUID.nameUUIDFromBytes(d2.getBytes()).toString());
        System.out.println(UUID.nameUUIDFromBytes(d3.getBytes()).toString());
        System.out.println("*************************");
        System.out.println(Hashing.murmur3_128().hashUnencodedChars(d0).asInt());
        System.out.println(Hashing.murmur3_128().hashUnencodedChars(d1).asInt());
        System.out.println(Hashing.murmur3_128().hashUnencodedChars(d2).asInt());
        System.out.println(Hashing.murmur3_128().hashUnencodedChars(d3).asInt());
    }
}
