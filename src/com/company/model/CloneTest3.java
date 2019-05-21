package com.company.model;

/**
 * @author ysy
 * @version v1.0
 * @description CloneTest
 * @date 2019-03-30 10:28
 */
public class CloneTest3 implements Cloneable {
    public String uu;
    public long ts;

    @Override
    public CloneTest3 clone() throws CloneNotSupportedException {
        return (CloneTest3) super.clone();
    }
}
