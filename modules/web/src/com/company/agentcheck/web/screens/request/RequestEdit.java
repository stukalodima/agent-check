package com.company.agentcheck.web.screens.request;

import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.Request;

@UiController("agentcheck_Request.edit")
@UiDescriptor("request-edit.xml")
@EditedEntityContainer("requestDc")
@LoadDataBeforeShow
public class RequestEdit extends StandardEditor<Request> {
}