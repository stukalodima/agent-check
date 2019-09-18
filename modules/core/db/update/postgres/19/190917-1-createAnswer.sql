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
    REQUEST_ID uuid not null,
    CLIENT varchar(255),
    --
    primary key (ID)
);