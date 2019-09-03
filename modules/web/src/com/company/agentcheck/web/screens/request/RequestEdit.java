package com.company.agentcheck.web.screens.request;

import com.company.agentcheck.entity.Request;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.gui.action.StartProcessAction;
import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.bpm.gui.proctask.ProcTasksFrame;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UiController("agentcheck_Request.edit")
@UiDescriptor("request-edit.xml")
@EditedEntityContainer("requestDc")
@LoadDataBeforeShow
public class RequestEdit extends StandardEditor<Request> {

    private static final String PROCESS_CODE = "express";

    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;

    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Inject
    private InstanceContainer<Request> requestDc;

    @Inject
    protected ProcActionsFragment procActionsFragment;

    @Inject
    private Table<ProcAttachment> attachmentsTable;

    @Inject
    private InstanceLoader<Request> requestDl;

    @Inject
    private ProcTasksFrame procTasksFrame;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    @Inject
    private BpmEntitiesService bpmEntitiesService;
    @Inject
    private UserSession userSession;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        procFrameInit();
    }

    @Subscribe
    private void onInitEntity(InitEntityEvent<Request> event) {
        long newNumber = uniqueNumbersService.getNextNumber("Request");
        String formattedNumber = String.format("%09d", newNumber);
        event.getEntity().setNumber(formattedNumber);
        event.getEntity().setStatus("Черновик");
        event.getEntity().setDocDate(new Date());
    }

    /** * Method demonstrates how to get and modify process actions automatically created by the ProcActionsFragment */
    private void changeStartProcessBtnCaption() {
        StartProcessAction startProcessAction = procActionsFragment.getStartProcessAction();
        if (startProcessAction != null) {
            startProcessAction.setCaption("Отправить заявку на проверку");
        }
    }

    private List<User> findUsersBySecRole(String secRoleName) {
        return dataManager.load(User.class)
                .query("select u from sec$User u join u.userRoles ur join ur.role r where r.name = :secRoleName")
                .parameter("secRoleName", secRoleName)
                .list();
    }

    private ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user) {
        ProcActor initiatorProcActor = metadata.create(ProcActor.class);
        initiatorProcActor.setUser(user);
        ProcRole initiatorProcRole = bpmEntitiesService.findProcRole(PROCESS_CODE, procRoleCode, View.MINIMAL);
        initiatorProcActor.setProcRole(initiatorProcRole);
        initiatorProcActor.setProcInstance(procInstance);
        return initiatorProcActor;
    }

    private void procFrameInit(){
        requestDl.load();
        procAttachmentsDl.setParameter("entityId",requestDc.getItem().getId());
        procAttachmentsDl.load();
        procActionsFragment.initializer()
                .standard()
                .setBeforeStartProcessPredicate(() ->{
                    if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {
                        ProcInstance procInstance = procActionsFragment.getProcInstance();
                        Set<ProcActor> procActors = new HashSet<>();
                        List<User> usersWithSecRole = findUsersBySecRole("СБ_СХ");
                        for (User user : usersWithSecRole) {
                            ProcActor initiatorProcActor = createProcActor("СБ_СХ", procInstance, user);
                            procActors.add(initiatorProcActor);
                        }
                        usersWithSecRole = findUsersBySecRole("СБ_СБС");
                        for (User user : usersWithSecRole) {
                            ProcActor initiatorProcActor = createProcActor("СБ_СБС", procInstance, user);
                            procActors.add(initiatorProcActor);
                        }
                        ProcActor initiatorProcActor = createProcActor("Инициатор", procInstance, userSession.getUser());
                        procActors.add(initiatorProcActor);
                        procInstance.setProcActors(procActors);
                        return true;
                    }
                    return false;
                })
                .setAfterCompleteTaskListener(()->{
                    requestDl.load();
                    procTasksFrame.refresh();
                    if (!getEditedEntity().getStatus().equals("Проверка завершена")){
                        procFrameInit();
                    } else {
                        procActionsFragment.getFragment().setVisible(false);
                    }
                })
                .setAfterStartProcessListener(()->{
                    requestDl.load();
                    procTasksFrame.refresh();
                    if (!getEditedEntity().getStatus().equals("Проверка завершена")){
                        procFrameInit();
                    } else {
                        procActionsFragment.getFragment().setVisible(false);
                    }
                })
                .init(PROCESS_CODE, getEditedEntity());

        ProcInstance procInstance = procActionsFragment.getProcInstance();
        procTasksFrame.setProcInstance(procInstance);
        procTasksFrame.refresh();

        if (getEditedEntity().getStatus().equals("Проверка завершена")){
            procActionsFragment.getFragment().setVisible(false);
        }

        changeStartProcessBtnCaption();

        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "file");
    }

}