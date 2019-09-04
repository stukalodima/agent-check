package com.company.agentcheck.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "AGENTCHECK_QUESTION")
@Entity(name = "agentcheck_Question")
public class Question extends StandardEntity {
    private static final long serialVersionUID = -8274075724241815123L;

    @NotNull
    @Lob
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}