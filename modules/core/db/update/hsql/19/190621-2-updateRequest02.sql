alter table AGENTCHECK_REQUEST alter column NUMBER_ rename to NUMBER___U50110 ^
alter table AGENTCHECK_REQUEST alter column DATE_ rename to DATE___U51954 ^
alter table AGENTCHECK_REQUEST alter column DATE___U51954 set null ;
alter table AGENTCHECK_REQUEST alter column AGENT_NAME rename to AGENT_NAME__U75933 ^
alter table AGENTCHECK_REQUEST alter column AGENT_NAME__U75933 set null ;
alter table AGENTCHECK_REQUEST add column AGENT_NAME varchar(255) ^
update AGENTCHECK_REQUEST set AGENT_NAME = '' where AGENT_NAME is null ;
alter table AGENTCHECK_REQUEST alter column AGENT_NAME set not null ;
