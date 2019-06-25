package com.company.agentcheck.web.screens.request;

import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.Request;

@UiController("agentcheck_Request.browse")
@UiDescriptor("request-browse.xml")
@LookupComponent("requestsTable")
@LoadDataBeforeShow
public class RequestBrowse extends StandardLookup<Request> {
}