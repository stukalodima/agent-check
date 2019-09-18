package com.company.agentcheck.core;

import com.company.agentcheck.entity.Request;
import com.google.common.base.Strings;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.entity.ReferenceToEntity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.UUID;

public class AgentTaskListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(AgentTaskListener.class);

    private Expression emailSubject;
    private Expression emailBody;

    @Override
    public void notify(DelegateTask delegateTask) {
        String emailSubjectValue = (String) emailSubject.getValue(delegateTask);
        String emailBodyValue = (String) emailBody.getValue(delegateTask);

        DataManager dataManager = AppBeans.get(DataManager.class);
        Set<IdentityLink> users = delegateTask.getCandidates();
        EmailerAPI emailerAPI = AppBeans.get(EmailerAPI.class);


//        String procId = delegateTask.getProcessInstanceId();
//        if (!procId.isEmpty()) {
//            //UUID procUUID = UUID.fromString(procId);
//            ProcInstance procInstance = dataManager.load(LoadContext.create(ProcInstance.class).setId(procId).setView(View.LOCAL));
//
//            UUID requestID = null;
//            if (procInstance != null) {
//                requestID = procInstance.getEntity().getEntityId();
//                Request request = dataManager.load(LoadContext.create(Request.class).setId(requestID).setView(View.LOCAL));
//
//                emailSubjectValue = emailSubjectValue.replaceAll("%Контрагент%", request != null ? request.getAgentName() : "");
//                emailBodyValue = emailBodyValue.replaceAll("%Контрагент%", request != null ? request.getAgentName() : "");
//                emailBodyValue = emailBodyValue.replaceAll("%ВидПроверки%", request != null ? request.getAgentName() : "");
//                emailBodyValue = emailBodyValue.replaceAll("%ЦельПроверки%", request != null ? request.getAgentName() : "");
//                emailBodyValue = emailBodyValue.replaceAll("%НазваниеПроверки%", request != null ? request.getAgentName() : "");
//            }
//        }

        if (users.isEmpty()) {
            String userStr = delegateTask.getAssignee();

            UUID userId = UUID.fromString(userStr);
            User user = dataManager.load(LoadContext.create(User.class)
                    .setId(userId)
                    .setView(View.LOCAL));
            if (user == null || Strings.isNullOrEmpty(user.getEmail())) {
                log.error("Email for user {} is not defined", user);
                return;
            }
            try {
                EmailInfo emailInfo = new EmailInfo(user.getEmail(),emailSubjectValue,emailBodyValue);

                emailInfo.setFrom("IT Service Management <ic.itil@smart-holding.com>");

                emailerAPI.sendEmail(emailInfo);
            } catch (EmailException e) {
                log.error("Email sending error", e);
            }
        } else {
            for (IdentityLink userIdent : users) {
                UUID userId = UUID.fromString(userIdent.getUserId());
                User user = dataManager.load(LoadContext.create(User.class)
                        .setId(userId)
                        .setView(View.LOCAL));
                if (user == null || Strings.isNullOrEmpty(user.getEmail())) {
                    log.error("Email for user {} is not defined", user);
                    return;
                }
                try {
                    EmailInfo emailInfo = new EmailInfo(user.getEmail(), emailSubjectValue, emailBodyValue);

                    emailInfo.setFrom("IT Service Management <ic.itil@smart-holding.com>");

                    emailerAPI.sendEmail(emailInfo);
                } catch (EmailException e) {
                    log.error("Email sending error", e);
                }
            }
        }
    }
}