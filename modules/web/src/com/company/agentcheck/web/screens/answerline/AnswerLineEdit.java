package com.company.agentcheck.web.screens.answerline;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.AnswerLine;

import javax.inject.Inject;

@UiController("agentcheck_AnswerLine.edit")
@UiDescriptor("answer-line-edit.xml")
@EditedEntityContainer("answerLineDc")
@LoadDataBeforeShow
public class AnswerLineEdit extends StandardEditor<AnswerLine> {
}