package com.company.agentcheck.web.screens.answer;

import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.Answer;

@UiController("agentcheck_Answer.browse")
@UiDescriptor("answer-browse.xml")
@LookupComponent("answersTable")
@LoadDataBeforeShow
public class AnswerBrowse extends StandardLookup<Answer> {
}