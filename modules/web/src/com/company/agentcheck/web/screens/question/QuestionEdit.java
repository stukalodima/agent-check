package com.company.agentcheck.web.screens.question;

import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.Question;

@UiController("agentcheck_Question.edit")
@UiDescriptor("question-edit.xml")
@EditedEntityContainer("questionDc")
@LoadDataBeforeShow
public class QuestionEdit extends StandardEditor<Question> {
}