create table AGENTCHECK_REQUEST (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CEL_PROVERKI integer not null,
    VID_PROVERKI integer not null,
    AGENT_NAME longvarchar not null,
    AGENT_CODE varchar(15) not null,
    COUNTRY varchar(255),
    JUR_ADDRESS varchar(255),
    FACT_ADDRESS varchar(255),
    DIRECTOR varchar(255),
    CONTRACT_SUMM varchar(255),
    CONTRACT_TYPE varchar(255),
    DETAIL longvarchar,
    --
    primary key (ID)
);