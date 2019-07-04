alter table AGENTCHECK_REQUEST add column DOC_DATE timestamp ^
update AGENTCHECK_REQUEST set DOC_DATE = current_timestamp where DOC_DATE is null ;
alter table AGENTCHECK_REQUEST alter column DOC_DATE set not null ;
alter table AGENTCHECK_REQUEST add column STATUS varchar(255) ;
-- alter table AGENTCHECK_REQUEST add column NUMBER_ varchar(9) ^
-- update AGENTCHECK_REQUEST set NUMBER_ = <default_value> ;
-- alter table AGENTCHECK_REQUEST alter column NUMBER_ set not null ;
alter table AGENTCHECK_REQUEST add column NUMBER_ varchar(9) ;
