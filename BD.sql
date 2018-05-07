create database SD1;

use SD1;

create table HoraCentral(
	ID int not null auto_increment,
	hPrev int not null,
	hRef int not null,
	primary key(ID)
);

create table Equipos(
	ID int not null auto_increment,
	IP varchar(100) not null,
	Nombre varchar(100) not null,
	Latencia varchar(100) not null,
	primary key(ID)
);

create table HoraEquipos(
	ID int not null auto_increment,
	IDhSincr int not null,
	IDEquipo int not null,
	hEquipo int not null,
	aEquipo int not null,
	ralentizar int not null,
	primary key(ID),
	foreign key(IDhSincr) references HoraCentral(ID),
	foreign key(IDEquipo) references Equipos(ID)
); 