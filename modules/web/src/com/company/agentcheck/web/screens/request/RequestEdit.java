package com.company.agentcheck.web.screens.request;

import com.company.agentcheck.entity.Answer;
import com.company.agentcheck.entity.AnswerLine;
import com.company.agentcheck.entity.Request;
import com.company.agentcheck.entity.RequestStatus;
import com.company.agentcheck.web.screens.answer.AnswerEdit;
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
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;

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
    @Inject
    private Button reportButton;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private PickerField<Answer> answerField;
    @Inject
    private GroupBoxLayout gbAttachments;
    @Inject
    private Button createAnswer;
    @Inject
    private Form form1;
    @Inject
    private Notifications notifications;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        reportButton.setAction(new EditorPrintFormAction("report", this, null));
        procFrameInit();
    }

    @Subscribe
    private void onInitEntity(InitEntityEvent<Request> event) {
        long newNumber = uniqueNumbersService.getNextNumber("Request");
        String formattedNumber = String.format("%09d", newNumber);
        event.getEntity().setNumber(formattedNumber);
        event.getEntity().setStatus(RequestStatus.DRAFT);
        event.getEntity().setDocDate(new Date());
    }

    private void changeStartProcessBtnCaption() {
        StartProcessAction startProcessAction = procActionsFragment.getStartProcessAction();
        if (startProcessAction != null) {
            startProcessAction.setCaption("Відправити анкету-заявку на перевірку");
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
                        List<ProcRole> procRoles = procInstance.getProcDefinition().getProcRoles();
                        for (ProcRole procRole:procRoles){
                            List<User> usersWithSecRole = findUsersBySecRole(procRole.getCode());
                            for (User user : usersWithSecRole) {
                                ProcActor initiatorProcActor = createProcActor(procRole.getCode(), procInstance, user);
                                procActors.add(initiatorProcActor);
                            }
                        }
                        ProcActor initiatorProcActor = createProcActor("CREATOR", procInstance, userSession.getUser());
                        procActors.add(initiatorProcActor);
                        procInstance.setProcActors(procActors);
                        return true;
                    }
                    return false;
                })
                .setBeforeCompleteTaskPredicate(()->{
                    if (getEditedEntity().getAnswer() != null){
                        return true;
                    }
                    notifications.create().withCaption("УВАГА!!!").withDescription("Потрібно провести перевірку").show();
                    return false;
                })
                .setAfterCompleteTaskListener(()->{
                    requestDl.load();
                    procTasksFrame.refresh();
                    if (!getEditedEntity().getStatus().equals(RequestStatus.FINISH)){
                        procFrameInit();
                    } else {
                        procActionsFragment.getFragment().setVisible(false);
                    }
                })
                .setAfterStartProcessListener(()->{
                    requestDl.load();
                    procTasksFrame.refresh();
                    if (!getEditedEntity().getStatus().equals(RequestStatus.FINISH)){
                        procFrameInit();
                    } else {
                        procActionsFragment.getFragment().setVisible(false);
                    }
                })
                .init(PROCESS_CODE, getEditedEntity());

        ProcInstance procInstance = procActionsFragment.getProcInstance();
        procTasksFrame.setProcInstance(procInstance);
        procTasksFrame.refresh();

        if (getEditedEntity().getStatus().equals(RequestStatus.FINISH)){
            procActionsFragment.getFragment().setVisible(false);
        }

        if (getEditedEntity().getStatus().equals(RequestStatus.DRAFT)){
            procTasksFrame.getFragment().setVisible(false);
            gbAttachments.setVisible(false);
        } else{
            procTasksFrame.getFragment().setVisible(true);
            gbAttachments.setVisible(true);
        }

        if (getEditedEntity().getStatus().equals(RequestStatus.PROCESS)
                || getEditedEntity().getStatus().equals(RequestStatus.DETAIL_PROCESS)){
            form1.setVisible(true);
            createAnswer.setVisible(true);
        } else{
            form1.setVisible(false);
            createAnswer.setVisible(false);
        }

        changeStartProcessBtnCaption();

        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "file");
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        if (getEditedEntity().getAnswer() != null){
            List<AnswerLine> answerLines = dataManager.load(AnswerLine.class)
                    .query("select e from agentcheck_AnswerLine e where e.answer.id = :answerID")
                    .parameter("answerID",getEditedEntity().getAnswer().getId()).list();
            double result = 1;
            for (AnswerLine answerLine:answerLines){
                result = result * answerLine.getPrice();
            }
            getEditedEntity().setResult(result);
        }
    }

    public void onCreateAnswerClick() {
        if (getEditedEntity().getAnswer() != null){
            screenBuilders.editor(answerField)
                    .editEntity(getEditedEntity().getAnswer())
                    .build()
                    .show();
        } else {
            screenBuilders.editor(answerField)
                .newEntity()
                .withInitializer(answer -> {
                    answer.setClient(getEditedEntity().getAgentName());
                    answer.setVidProverki(getEditedEntity().getVidProverki());
                    answer.setRequest(getEditedEntity());
                })
                .withScreenClass(AnswerEdit.class)
                .withLaunchMode(OpenMode.NEW_TAB)
                .build()
                .show();
        }
    }

}