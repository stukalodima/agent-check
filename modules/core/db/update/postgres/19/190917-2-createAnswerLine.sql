alter table AGENTCHECK_ANSWER_LINE add constraint FK_AGENTCHECK_ANSWER_LINE_ON_ANSWER foreign key (ANSWER_ID) references AGENTCHECK_ANSWER(ID);
alter table AGENTCHECK_ANSWER_LINE add constraint FK_AGENTCHECK_ANSWER_LINE_ON_QUESTION foreign key (QUESTION_ID) references AGENTCHECK_QUESTION(ID);
create index IDX_AGENTCHECK_ANSWER_LINE_ON_ANSWER on AGENTCHECK_ANSWER_LINE (ANSWER_ID);
create index IDX_AGENTCHECK_ANSWER_LINE_ON_QUESTION on AGENTCHECK_ANSWER_LINE (QUESTION_ID);
