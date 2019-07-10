package com.company.agentcheck.core;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class AgentTaskListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(AgentTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getAssignee();
        EmailerAPI emailerAPI = AppBeans.get(EmailerAPI.class);
        DataManager dataManager = AppBeans.get(DataManager.class);
        UUID userId = UUID.fromString(assignee);
        User user = dataManager.load(LoadContext.create(User.class)
                .setId(userId)
                .setView(View.LOCAL));
        if (user == null || Strings.isNullOrEmpty(user.getEmail())) {
            log.error("Email for user {} is not defined", user);
            return;
        }
        try {
            emailerAPI.sendEmail(user.getEmail(), "Email subject", "Email body");
        } catch (EmailException e) {
            log.error("Email sending error", e);
        }
    }
}