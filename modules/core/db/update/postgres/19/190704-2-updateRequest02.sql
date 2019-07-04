-- update AGENTCHECK_REQUEST set NUMBER_ = <default_value> where NUMBER_ is null ;
alter table AGENTCHECK_REQUEST alter column NUMBER_ set not null ;
