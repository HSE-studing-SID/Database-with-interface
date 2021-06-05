CREATE TYPE АКТИВНОСТЬ AS ENUM
(
	'Плавание',
	'Силовая тренировка',
	'Растяжка'
	-- // todo add more
);

CREATE TYPE МЕСТО AS ENUM 
(
    'Бассейн',
    'Тренажерный зал',
    'Малый зал',
    'Большой зал'
);

CREATE TABLE ДОЛЖНОСТЬ
(
    ИДЕНТИФИКАТОР             SERIAL          PRIMARY KEY,
    НАЗВАНИЕ                  VARCHAR(64)     NOT NULL,
    "РАБОЧЕЕ ВРЕМЯ ЗА ДЕНЬ"   SMALLINT        NOT NULL,
    "ПЛАТА ЗА ЧАС"            SMALLINT        NOT NULL,
    CHECK ("РАБОЧЕЕ ВРЕМЯ ЗА ДЕНЬ" > 0 AND "ПЛАТА ЗА ЧАС" > 0)
);

CREATE TABLE КЛИЕНТ
(
    ИДЕНТИФИКАТОР       SERIAL          PRIMARY KEY,
    ИМЯ                 VARCHAR(64)     NOT NULL,
    ФАМИЛИЯ             VARCHAR(64)     NOT NULL,
    ОТЧЕСТВО            VARCHAR(64)     NOT NULL,
    ТЕЛЕФОН             INTEGER         NOT NULL,
    "ПАССПОРТ, СЕРИЯ"   SERIAL          NOT NULL,
    "ПАССПОРТ, НОМЕР"   SERIAL          NOT NULL
);

CREATE TABLE АБОНЕМЕНТ
(
    ИДЕНТИФИКАТОР                SERIAL         PRIMARY KEY,
    ЦЕНА             	         SMALLINT    	NOT NULL,
    "ПРОДОЛЖИТЕЛЬНОСТЬ, мес"     SMALLINT    	NOT NULL,
    НАЗВАНИЕ                     VARCHAR(64)    NOT NULL,
    "РАЗРЕШЕННЫЕ АКТИВНОСТИ"     АКТИВНОСТЬ[]   NOT NULL
);

CREATE TABLE РАБОТНИК
(
    ИДЕНТИФИКАТОР       SERIAL          PRIMARY KEY,
    ДОЛЖНОСТЬ           SERIAL          NOT NULL REFERENCES ДОЛЖНОСТЬ (ИДЕНТИФИКАТОР),
    ИМЯ                 VARCHAR(64)     NOT NULL,
    ФАМИЛИЯ             VARCHAR(64)     NOT NULL,
    ОТЧЕСТВО            VARCHAR(64)     NOT NULL,
    ТЕЛЕФОН             INTEGER         NOT NULL
);

CREATE TABLE ДОГОВОР
(
    ИДЕНТИФИКАТОР           SERIAL          PRIMARY KEY,
    КЛИЕНТ                  SERIAL          NOT NULL REFERENCES КЛИЕНТ (ИДЕНТИФИКАТОР),
    АБОНЕМЕНТ 	            SMALLINT        NOT NULL REFERENCES АБОНЕМЕНТ (ИДЕНТИФИКАТОР),
    "НАЧАЛО ДЕЙСТВИЯ"       DATE            NOT NULL,
    "КОНЕЦ ДЕЙСТВИЯ"        DATE            NOT NULL,
    CHECK("НАЧАЛО ДЕЙСТВИЯ" < "КОНЕЦ ДЕЙСТВИЯ")
);

CREATE TABLE РАСПИСАНИЕ
(
    ИДЕНТИФИКАТОР           SERIAL          PRIMARY KEY,
    ТРЕНЕР                  SERIAL          NOT NULL REFERENCES РАБОТНИК (ИДЕНТИФИКАТОР),
    АКТИВНОСТЬ              АКТИВНОСТЬ      NOT NULL,
    ЗАЛ                     МЕСТО           NOT NULL,
    НАЧАЛО                  TIMESTAMP 		NOT NULL,
    КОНЕЦ                   TIMESTAMP       NOT NULL
-- CHECK() // todo
);
