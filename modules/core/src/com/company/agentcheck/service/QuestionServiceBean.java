package com.company.agentcheck.service;

import com.company.agentcheck.core.RequestHelper;
import com.company.agentcheck.entity.Question;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(QuestionService.NAME)
public class QuestionServiceBean implements QuestionService {

    @Inject
    private RequestHelper requestHelper;

    @Override
    public List<Question> getQuestionList(Integer celProverki) {
        return requestHelper.getQuestionList(celProverki);
    }
}