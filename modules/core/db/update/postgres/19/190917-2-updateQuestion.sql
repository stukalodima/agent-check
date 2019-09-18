alter table AGENTCHECK_QUESTION add column VID_PROVERKI integer ^
update AGENTCHECK_QUESTION set VID_PROVERKI = 1 where VID_PROVERKI is null ;
alter table AGENTCHECK_QUESTION alter column VID_PROVERKI set not null ;
