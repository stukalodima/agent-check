package com.company.agentcheck.core;

import com.company.agentcheck.entity.Request;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component(RequestHelper.NAME)
public class RequestHelper {
    public static final String NAME = "agentcheck_RequestHelper";

    @Inject
    private Persistence persistence;

    @Inject
    private DataManager dataManager;

    @Inject
    private Logger log;

    @Inject
    private Metadata metadata;

    public void updateState(UUID entityId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            Request request = persistence.getEntityManager().find(Request.class, entityId);
            if (request != null) {
                request.setStatus(state);
            }
            tx.commit();
        }
    }
}