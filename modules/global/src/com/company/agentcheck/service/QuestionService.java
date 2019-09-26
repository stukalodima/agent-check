package com.company.agentcheck.service;

import com.company.agentcheck.entity.Question;

import java.util.List;

public interface QuestionService {
    String NAME = "agentcheck_QuestionService";

    List<Question> getQuestionList(Integer celProverki);
}