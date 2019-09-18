alter table AGENTCHECK_ANSWER add column VID_PROVERKI integer ^
update AGENTCHECK_ANSWER set VID_PROVERKI = 1 where VID_PROVERKI is null ;
alter table AGENTCHECK_ANSWER alter column VID_PROVERKI set not null ;
