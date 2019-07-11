package com.company.agentcheck.core;

import com.company.agentcheck.entity.Request;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public void fillProcRoles(String procRoleCode, String secRoleName, UUID bpmProcInstanceId) {
//        ProcInstance procInstance = dataManager.load(LoadContext.create(ProcInstance.class)
//                .setId(bpmProcInstanceId)
//                .setView("procInstance-listener-view"));
        ProcInstance procInstance = persistence.getEntityManager().find(ProcInstance.class, bpmProcInstanceId);

        if (procInstance == null){
            log.error("Procces with id {} not found", bpmProcInstanceId);
            return;
        }
        Optional<ProcRole> procRoleOpt = procInstance.getProcDefinition().getProcRoles().stream()
                .filter(procRole -> procRole.getCode().equals(procRoleCode))
                .findAny();
        if (!procRoleOpt.isPresent()) {
            log.error("ProcRole {} not found", procRoleCode);
            return;
        }
        List<User> usersWithSecRole = findUsersBySecRole(secRoleName);
        List<ProcActor> procActorsToCreate = new ArrayList<>();
        for (User user : usersWithSecRole) {
            ProcActor procActor = metadata.create(ProcActor.class);
            procActor.setUser(user);
            procActor.setProcInstance(procInstance);
            procActor.setProcRole(procRoleOpt.get());
            procActorsToCreate.add(procActor);
        }
        dataManager.commit(new CommitContext(procActorsToCreate));
    }

    private List<User> findUsersBySecRole(String secRoleName) {
        return dataManager.load(User.class)
                .query("select u from sec$User u join u.userRoles ur join ur.role r where r.name = :secRoleName")
                .parameter("secRoleName", secRoleName)
                .list();
    }
}