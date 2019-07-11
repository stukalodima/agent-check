package com.company.agentcheck.core;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AgentCreateTaskListener implements TaskListener {

    private static final Logger log = LoggerFactory.getLogger(AgentTaskListener.class);

    private Expression procRole;

    @Override
    public void notify(DelegateTask delegateTask) {
        String procRoleValue = (String) procRole.getValue(delegateTask);
        List<User> usersWithSecRole = findUsersBySecRole(procRoleValue);
        for (User user : usersWithSecRole) {
            delegateTask.addCandidateUser(user.getId().toString());
        }
    }

    private List<User> findUsersBySecRole(String secRoleName) {
        DataManager dataManager = AppBeans.get(DataManager.class);
        return dataManager.load(User.class)
                .query("select u from sec$User u join u.userRoles ur join ur.role r where r.name = :secRoleName")
                .parameter("secRoleName", secRoleName)
                .list();

    }
}