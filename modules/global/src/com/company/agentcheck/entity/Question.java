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

    @NotNull
    @Column(name = "VID_PROVERKI", nullable = false)
    protected java.lang.Integer vidProverki;

    @Column(name = "CEL_PROVERKI")
    protected Integer celProverki;

    public CeliProverki getCelProverki() {
        return celProverki == null ? null : CeliProverki.fromId(celProverki);
    }

    public void setCelProverki(CeliProverki celProverki) {
        this.celProverki = celProverki == null ? null : celProverki.getId();
    }

    public ViduProverki getVidProverki() {
        return vidProverki == null ? null : ViduProverki.fromId(vidProverki);
    }

    public void setVidProverki(ViduProverki vidProverki) {
        this.vidProverki = vidProverki == null ? null : vidProverki.getId();
    }

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