alter table AGENTCHECK_REQUEST add constraint FK_AGENTCHECK_REQUEST_ON_AUTOR foreign key (AUTOR_ID) references SEC_USER(ID);
create index IDX_AGENTCHECK_REQUEST_ON_AUTOR on AGENTCHECK_REQUEST (AUTOR_ID);
