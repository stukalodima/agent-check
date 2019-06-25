alter table AGENTCHECK_REQUEST add column DATE_ timestamp ^
update AGENTCHECK_REQUEST set DATE_ = current_timestamp where DATE_ is null ;
alter table AGENTCHECK_REQUEST alter column DATE_ set not null ;
alter table AGENTCHECK_REQUEST add column NUMBER_ varchar(9) ;
