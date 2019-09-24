package com.company.agentcheck.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum RezNerez implements EnumClass<Integer> {

    REZ(1),
    NEREZ(2);

    private Integer id;

    RezNerez(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static RezNerez fromId(Integer id) {
        for (RezNerez at : RezNerez.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}