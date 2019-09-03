package com.company.agentcheck.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamePattern("%s - %s|number,agentName")
@Table(name = "AGENTCHECK_REQUEST")
@Entity(name = "agentcheck_Request")
public class Request extends StandardEntity {
    private static final long serialVersionUID = 5727013000720594771L;

    @NotNull
    @Column(name = "NUMBER_", nullable = false, unique = true, length = 9)
    protected String number;

    @Column(name = "RESULT_")
    protected Double result;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "DOC_DATE", nullable = false)
    protected Date docDate;

    @Column(name = "STATUS")
    protected String status;

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

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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