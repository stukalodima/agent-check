package com.company.agentcheck.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum JurFiz implements EnumClass<Integer> {

    JUR(1),
    FIZ(2);

    private Integer id;

    JurFiz(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static JurFiz fromId(Integer id) {
        for (JurFiz at : JurFiz.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}