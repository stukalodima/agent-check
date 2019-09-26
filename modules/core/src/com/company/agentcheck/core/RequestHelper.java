package com.company.agentcheck.core;

import com.company.agentcheck.entity.Question;
import com.company.agentcheck.entity.Request;
import com.company.agentcheck.entity.RequestStatus;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Component(RequestHelper.NAME)
public class RequestHelper {
    public static final String NAME = "agentcheck_RequestHelper";

    @Inject
    private Persistence persistence;

    public void updateState(UUID entityId, int state) {
        RequestStatus stateValue = RequestStatus.fromId(state);
        try (Transaction tx = persistence.getTransaction()) {
            Request request = persistence.getEntityManager().find(Request.class, entityId);
            if (request != null) {
                request.setStatus(stateValue);
            }
            tx.commit();
        }
    }

    public List<Question> getQuestionList(Integer celProverki){
        List<Question> questionList = null;
        try (Transaction transaction = persistence.getTransaction()) {
            String strQuery = "select e.* from agentcheck_Question e where e.cel_proverki = #celId";
            TypedQuery<Question> query = persistence.getEntityManager().createNativeQuery(strQuery, Question.class);
            query.setParameter("celId", celProverki);
            questionList = query.getResultList();
            transaction.commit();
        }
        return questionList;

    }
}