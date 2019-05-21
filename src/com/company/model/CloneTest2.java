package com.company.model;

/**
 * @author ysy
 * @version v1.0
 * @description CloneTest
 * @date 2019-03-30 10:28
 */
public class CloneTest2 implements Cloneable {
    public String uu;
    public long ts;

    @Override
    public CloneTest2 clone() {
        try {
            return (CloneTest2) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }
}
