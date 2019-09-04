package com.company.agentcheck.web.screens.question;

import com.haulmont.cuba.gui.screen.*;
import com.company.agentcheck.entity.Question;

@UiController("agentcheck_Question.browse")
@UiDescriptor("question-browse.xml")
@LookupComponent("questionsTable")
@LoadDataBeforeShow
public class QuestionBrowse extends StandardLookup<Question> {
}