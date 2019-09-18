package com.company.agentcheck.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NamePattern("%s %s %s %s|vidProverki,number,client,docDate")
@Table(name = "AGENTCHECK_ANSWER")
@Entity(name = "agentcheck_Answer")
public class Answer extends StandardEntity {
    private static final long serialVersionUID = 7523056850163037693L;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "DOC_DATE", nullable = false)
    protected Date docDate;

    @Column(name = "NUMBER_", unique = true, length = 11)
    protected String number;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @NotNull
    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_ID")
    protected Request request;

    @NotNull
    @Column(name = "VID_PROVERKI", nullable = false)
    protected Integer vidProverki;

    @NotNull
    @Column(name = "CLIENT")
    protected String client;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "answer", cascade = CascadeType.PERSIST)
    protected List<AnswerLine> lines;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ViduProverki getVidProverki() {
        return vidProverki == null ? null : ViduProverki.fromId(vidProverki);
    }

    public void setVidProverki(ViduProverki vidProverki) {
        this.vidProverki = vidProverki == null ? null : vidProverki.getId();
    }

    public List<AnswerLine> getLines() {
        return lines;
    }

    public void setLines(List<AnswerLine> lines) {
        this.lines = lines;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }
}