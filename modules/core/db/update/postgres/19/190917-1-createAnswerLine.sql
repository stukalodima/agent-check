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
    QUESTION_ID uuid not null,
    PRICE decimal(19, 2) not null,
    VALUE_ boolean,
    --
    primary key (ID)
);