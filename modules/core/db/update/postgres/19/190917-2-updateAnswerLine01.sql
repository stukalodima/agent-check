alter table AGENTCHECK_ANSWER_LINE rename column price to price__u75157 ;
alter table AGENTCHECK_ANSWER_LINE alter column price__u75157 drop not null ;
alter table AGENTCHECK_ANSWER_LINE add column PRICE double precision ^
update AGENTCHECK_ANSWER_LINE set PRICE = 0 where PRICE is null ;
alter table AGENTCHECK_ANSWER_LINE alter column PRICE set not null ;
