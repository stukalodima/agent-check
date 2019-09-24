-- begin AGENTCHECK_REQUEST
create table AGENTCHECK_REQUEST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(9) not null,
    RESULT_ double precision,
    DOC_DATE timestamp not null,
    STATUS integer,
    CEL_PROVERKI integer not null,
    VID_PROVERKI integer not null,
    AGENT_NAME varchar(255) not null,
    AGENT_CODE varchar(15) not null,
    COUNTRY varchar(255),
    JUR_ADDRESS varchar(255),
    FACT_ADDRESS varchar(255),
    DIRECTOR varchar(255),
    CONTRACT_SUMM varchar(255),
    CONTRACT_TYPE varchar(255),
    DETAIL text,
    JUR_FIZ integer,
    REZ_NEREZ integer,
    AUTOR_ID uuid,
    --
    primary key (ID)
)^
-- end AGENTCHECK_REQUEST
-- begin AGENTCHECK_QUESTION
create table AGENTCHECK_QUESTION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME text not null,
    PRICE double precision not null,
    VID_PROVERKI integer not null,
    --
    primary key (ID)
)^
-- end AGENTCHECK_QUESTION
-- begin AGENTCHECK_ANSWER
create table AGENTCHECK_ANSWER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOC_DATE timestamp not null,
    NUMBER_ varchar(11),
    USER_ID uuid,
    REQUEST_ID uuid,
    VID_PROVERKI integer not null,
    CLIENT varchar(255),
    --
    primary key (ID)
)^
-- end AGENTCHECK_ANSWER
-- begin AGENTCHECK_ANSWER_LINE
create table AGENTCHECK_ANSWER_LINE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ANSWER_ID uuid not null,
    QUESTION_ID uuid,
    PRICE double precision not null,
    VALUE_ boolean,
    --
    primary key (ID)
)^
-- end AGENTCHECK_ANSWER_LINE
