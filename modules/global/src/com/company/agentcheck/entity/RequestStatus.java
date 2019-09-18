package com.company.agentcheck.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum RequestStatus implements EnumClass<Integer> {

    DRAFT(1),
    CANCEL(2),
    CHECK_DOC(3),
    ADD_DOCS(4),
    PROCESS(5),
    DETAIL_PROCESS(6),
    GET_RESULT(7),
    FINISH(8);

    private Integer id;

    RequestStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static RequestStatus fromId(Integer id) {
        for (RequestStatus at : RequestStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}