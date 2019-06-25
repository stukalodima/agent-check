package com.company.agentcheck.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CeliProverki implements EnumClass<Integer> {

    CANDIDAT(1),
    JURRESIDENT(2),
    JURNOTRESIDENT(3),
    SPD(4);

    private Integer id;

    CeliProverki(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static CeliProverki fromId(Integer id) {
        for (CeliProverki at : CeliProverki.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}