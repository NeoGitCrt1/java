package com.company;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author ysy
 * @version v1.0
 * @description Snow
 * @date 2018-12-29 13:54
 */
public class Snow {

    private final static long twepoch = 1409030641843L;
    // 机器标识位数
    private final static long workerIdBits = 9L;
    // 数据中心标识位数
    private final static long dataCenterIdBits = 1L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值
//	private final static long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long dataCenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private static long sequence = 0L;

    private static long workerId = 1;

    private static final long dataCenterId = 1L;

    private static final HashFunction HASH_FUNCTION32 = Hashing.murmur3_32();

    public static void main(String[] aaa) throws InterruptedException {
        long lastTimestamp = System.currentTimeMillis();
        workerId = HASH_FUNCTION32.hashUnencodedChars("192.168.0.0:8848").padToLong();
        Thread.sleep(1000);
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    "Clock moved backwards.  Refusing to generate id for " + (lastTimestamp
                            - timestamp) + " milliseconds");
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId =
                ((timestamp - twepoch) << timestampLeftShift) | (dataCenterId << dataCenterIdShift) | (
                        workerId << workerIdShift) | sequence;

        System.out.println(nextId);
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
