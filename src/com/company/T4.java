package com.company;

import java.util.UUID;

/**
 * @author ysy
 * @version v1.0
 * @description T4
 * @date 2019-05-09 14:36
 */
public class T4 {
    public static void main(String[] aa) {
        System.out.println(UUID.randomUUID().toString().replace("-","").toUpperCase());



        trans(11500);
        trans(10500);
        trans(9500);
        trans(8500);
        trans(7500);
        trans(6500);
        trans(5500);
        trans(4500);

    }

    private static void trans(final int amt) {
        System.out.println("************************" + amt);
        int left = 0;

        while (true) {
            left += 5000;
            if (left > amt) {
                int rem = amt - left + 5000;
                if (rem > 0) {
                    System.out.println(">>>trans" + rem);
                }
                break;
            }
            System.out.println(">>>trans" + 5000);

        }

    }
}
