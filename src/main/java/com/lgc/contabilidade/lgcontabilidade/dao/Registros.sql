use lgc

CREATE TABLE Registros (
	dat varchar(20),
	entrada varchar(10),
    saidaAlmoco varchar(10),
    voltaAlmoco varchar(10),
    saidaCasa varchar(10),
    total varchar(10),
    extras varchar(10),
    somaHoras int,
    id int
);

SELECT * FROM Registros;
DROP TABLE Registros;
DELETE FROM Registros WHERE saidaCasa = '15:00';