package com.company.agentcheck.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ViduProverki implements EnumClass<Integer> {

    EXPRESS(1),
    KOMPLEX(2),
    UGLUBLENKA(3);

    private Integer id;

    ViduProverki(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ViduProverki fromId(Integer id) {
        for (ViduProverki at : ViduProverki.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}