CREATE TABLE characters
(
    id       UUID        NOT NULL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    role     VARCHAR(20) NOT NULL,
    school   VARCHAR(60) NOT NULL,
    house    VARCHAR(40) NOT NULL,
    patronus VARCHAR(50)
);