package com.company.agentcheck.web.screens.request;

import com.company.agentcheck.entity.Request;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.gui.action.StartProcessAction;
import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.bpm.gui.proctask.ProcTasksFrame;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.Date;

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

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        requestDl.load();
        procAttachmentsDl.setParameter("entityId",requestDc.getItem().getId());
        procAttachmentsDl.load();
        procActionsFragment.initializer()
                .standard()
                .init(PROCESS_CODE, getEditedEntity());



        ProcInstance procInstance = procActionsFragment.getProcInstance();
        procTasksFrame.setProcInstance(procInstance);
        procTasksFrame.refresh();

        changeStartProcessBtnCaption();

        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "file");
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
            startProcessAction.setCaption("Start process using ProcActionsFragment");
        }
    }

}