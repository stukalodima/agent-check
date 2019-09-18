package com.company.agentcheck.web.screens.answer;

import com.company.agentcheck.entity.Answer;
import com.company.agentcheck.entity.AnswerLine;
import com.company.agentcheck.entity.Question;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@UiController("agentcheck_Answer.edit")
@UiDescriptor("answer-edit.xml")
@EditedEntityContainer("answerDc")
@LoadDataBeforeShow
public class AnswerEdit extends StandardEditor<Answer> {
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionPropertyContainer<AnswerLine> linesDc;
    @Inject
    private Metadata metadata;
    @Inject
    private EntityStates entityStates;
    @Inject
    private UserSession userSession;

    @Subscribe
    private void onInitEntity(InitEntityEvent<Answer> event) {
        long newNumber = uniqueNumbersService.getNextNumber("Answer");
        String formattedNumber = String.format("%09d", newNumber);
        event.getEntity().setNumber(formattedNumber);
        event.getEntity().setDocDate(new Date());
        event.getEntity().setUser(userSession.getUser());
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        if (entityStates.isNew(getEditedEntity())){
            fillAnswerLines();
        }
    }

    private void fillAnswerLines() {
        List<Question> list = dataManager.load(Question.class).query("select e from agentcheck_Question e").list();

        for (Question question:list){
            AnswerLine answerLine = metadata.create(AnswerLine.class);
            answerLine.setQuestion(question);
            answerLine.setAnswer(getEditedEntity());
            answerLine.setPrice(question.getPrice());
            answerLine.setValue(false);
            linesDc.getMutableItems().add(answerLine);
        }
    }
}