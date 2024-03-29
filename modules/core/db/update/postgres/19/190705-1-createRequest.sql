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
    DOC_DATE timestamp not null,
    STATUS varchar(255),
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
    --
    primary key (ID)
);