package com.company.agentcheck.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern(" |")
@Table(name = "AGENTCHECK_REQUEST")
@Entity(name = "agentcheck_Request")
public class Request extends StandardEntity {
    private static final long serialVersionUID = 5727013000720594771L;

    @NotNull
    @Column(name = "CEL_PROVERKI", nullable = false)
    protected Integer celProverki;

    @NotNull
    @Column(name = "VID_PROVERKI", nullable = false)
    protected Integer vidProverki;

    @NotNull
    @Column(name = "AGENT_NAME", nullable = false)
    protected String agentName;

    @NotNull
    @Column(name = "AGENT_CODE", nullable = false, length = 15)
    protected String agentCode;

    @Column(name = "COUNTRY")
    protected String country;

    @Column(name = "JUR_ADDRESS")
    protected String jurAddress;

    @Column(name = "FACT_ADDRESS")
    protected String factAddress;

    @Column(name = "DIRECTOR")
    protected String director;

    @Column(name = "CONTRACT_SUMM")
    protected String contractSumm;

    @Column(name = "CONTRACT_TYPE")
    protected String contractType;

    @Lob
    @Column(name = "DETAIL")
    protected String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractSumm() {
        return contractSumm;
    }

    public void setContractSumm(String contractSumm) {
        this.contractSumm = contractSumm;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getFactAddress() {
        return factAddress;
    }

    public void setFactAddress(String factAddress) {
        this.factAddress = factAddress;
    }

    public String getJurAddress() {
        return jurAddress;
    }

    public void setJurAddress(String jurAddress) {
        this.jurAddress = jurAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public ViduProverki getVidProverki() {
        return vidProverki == null ? null : ViduProverki.fromId(vidProverki);
    }

    public void setVidProverki(ViduProverki vidProverki) {
        this.vidProverki = vidProverki == null ? null : vidProverki.getId();
    }

    public CeliProverki getCelProverki() {
        return celProverki == null ? null : CeliProverki.fromId(celProverki);
    }

    public void setCelProverki(CeliProverki celProverki) {
        this.celProverki = celProverki == null ? null : celProverki.getId();
    }
}