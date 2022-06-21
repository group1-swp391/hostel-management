create database Hostel_Management7
go
use Hostel_Management7
go

GO
CREATE TABLE tbl_Role(
	roleID int IDENTITY(1,1) PRIMARY KEY,
	roleName varchar(20) NOT NULL,
)
GO
INSERT INTO tbl_Role(roleName) values('Admin')
INSERT INTO tbl_Role(roleName) values('Host')
INSERT INTO tbl_Role(roleName) values('Renter')
GO
go
Create table tbl_Users(
	userID int IDENTITY(1,1) PRIMARY KEY,
	userName varchar(20) UNIQUE,
	password varchar(20) NOT NULL,
	fullName nvarchar(50) NOT NULL,
	dateOfBirth date NOT NULL,
	gender bit,
	phone char(10) UNIQUE NOT NULL,
	email varchar(50) UNIQUE NOT NULL,
	documentID char(12) UNIQUE,
	documentFrontSide image,
	documentBackSide image,
	roleID int FOREIGN KEY references tbl_Role(roleID) NOT NULL,
	userStatus bit NOT NULL,
	REGTIME smalldatetime DEFAULT CURRENT_TIMESTAMP,
)
go
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('hukhho','hunghung','Xuan Hung','2009-01-05',1,'0387788999','hukhho@gmail.com','24121999',2,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('namvip','hunghung','Nguyen Nam','2008-08-08',1,'0387788777','naam@gmail.com','212233555',2,1)

INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao','thao123','Phuong Thao','2007-01-05',1,'0387777777','thao@gmail.com','212312555',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('phong','phong999','Kieu Phong','2005-08-08',1,'0387788666','kieuphong@gmail.com','267812566',3,1)
go
select * from tbl_Users
go
CREATE TABLE tbl_Hostel(
	hostelID  int IDENTITY(1,1) PRIMARY KEY,
	ownerHostelID int NOT NULL,
	address varchar(255) not null,
	hostelName varchar(50) not null,
	hostelStatus bit not null,

	imageHostel varchar(1000),

	CONSTRAINT FK_userManage FOREIGN KEY (ownerHostelID) REFERENCES tbl_Users(userID)
)
go

INSERT INTO tbl_Hostel(ownerHostelID,address,hostelName,hostelStatus,imageHostel) values(1,'123 Le Van Viet','Nam Hostel',1,'https://cdn.thongtinduan.com/uploads/posts/2020-05/tphcm-ban-hanh-quy-chuan-xay-nha-tro-1.jpg')
INSERT INTO tbl_Hostel(ownerHostelID,address,hostelName,hostelStatus,imageHostel) values(2,'22 Hoang Huu Nam','Hung Hostel',1,'http://nhaankhang.net/wp-content/uploads/2018/04/nhatro0.jpg')
select * from tbl_Hostel
go
Create table tbl_RoomType(
	typeID int IDENTITY(1,1) PRIMARY KEY,
	description varchar(200) not null,
	price float NOT NULL,
	depositPrice float not null,
	roomName varchar (100) not null,
	roomTypeStatus bit not null, 
	hostelID int NOT NULL,


	imageRoomType varchar(1000),

	CONSTRAINT FK_roomType
	FOREIGN KEY (hostelID)
	REFERENCES tbl_Hostel(hostelID),	
)
GO
INSERT INTO tbl_RoomType(hostelID,description,price,depositPrice,roomName,roomTypeStatus,imageRoomType) values (1,'rong 50m2',3000000,5000000,'Phong rong',1,'https://datnendep.vn/wp-content/uploads/2019/10/anh-phong-tro-1_1545126166.jpg')
INSERT INTO tbl_RoomType(hostelID,description,price,depositPrice,roomName,roomTypeStatus,imageRoomType) values (1,'rong 20m2',2000000,3000000,'Phong nho',1,'https://angcovat.vn/imagesdata/KN208117/thiet-ke-phong-tro-khep-kin-co-gac-lung.jpg')

INSERT INTO tbl_RoomType(hostelID,description,price,depositPrice,roomName,roomTypeStatus,imageRoomType) values (2,'rong 99m2 co tu lanh',5500000,7000000,'VIP 1',1,'https://xaydungtienthanh.vn/wp-content/uploads/2020/09/xay-10-phong-tro-bao-nhieu-tien-5.jpg')
INSERT INTO tbl_RoomType(hostelID,description,price,depositPrice,roomName,roomTypeStatus,imageRoomType) values (2,'rong 20m2',2000000,3000000,'VIP 2',1,'https://lucihouse.com/xay-nha-tro/imager_16852.jpg')

go
Create table tbl_Room(
	roomID int IDENTITY(1,1) PRIMARY KEY,
	roomNumber int NOT NULL,
	UserID int,
	typeID int NOT NULL,
	roomStatus bit NOT NULL,
	image image,
	CONSTRAINT FK_roomUser
	FOREIGN KEY (userID)
	REFERENCES tbl_Users(userID),
	CONSTRAINT FK_roomRoomType
	FOREIGN KEY (typeID)
	REFERENCES tbl_RoomType(typeID)
)

go
ALTER TABLE tbl_Room 
  ADD CONSTRAINT roomNumberAndType 
  UNIQUE (roomNumber,typeID)
go
 
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',1,1)

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',2,1)

SELECT * from tbl_RoomType
SELECT * from tbl_Room

go
Create table tbl_Invoice(
	invoiceID int IDENTITY(1,1) PRIMARY KEY,
	roomID int not null,
	invoiceName varchar(50) not null,
	totalAmount float not null,
	invoiceStatus bit not null,
	note varchar(255),
	invoiceCreateDate smalldatetime default CURRENT_TIMESTAMP,
	paymentDate smalldatetime,

	CONSTRAINT FK_InvoiceRoom
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID),
)
go

go
create table tbl_Contracts(
	contractID int IDENTITY(1,1) PRIMARY KEY,
	startDate datetime not null,
	endDate datetime not null,
	deposit float not null,
	userID int not null,
	roomID int not null,
	contractStatus bit not null,
	createContractTime smalldatetime DEFAULT CURRENT_TIMESTAMP,
	contractLiquidationTime smalldatetime,

	CONSTRAINT FK_contractUser
	FOREIGN KEY (userID)
	REFERENCES tbl_Users(userID),

	CONSTRAINT FK_contractRoom
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID)
)
go


GO
CREATE TABLE tbl_ServiceType(
	serviceTypeID int IDENTITY(1,1) PRIMARY KEY,
	serviceName varchar(50) not null,
	price float,
	hostelID int,
	CONSTRAINT FK_ServiceTypeHostelID FOREIGN KEY (hostelID) REFERENCES tbl_Hostel(hostelID)
)
GO

go
CREATE TABLE tbl_UtilityType(
	utilityTypeID int IDENTITY(1,1) PRIMARY KEY,
	utilityName varchar(50) not null,
	pricePerIndex float,
	hostelID int,
	CONSTRAINT FK_UtilityTypehostelID FOREIGN KEY (hostelID) REFERENCES tbl_Hostel(hostelID)
)
GO

go
CREATE TABLE tbl_UsedUtility(
	usedUtilityID int IDENTITY(1,1) PRIMARY KEY,
	roomID int not null,
	startDate datetime not null,
	endDate datetime not null,
	utilityTypeID int not null,
	oldIndex int not null,
	newIndex int not null,
	pricePerIndex float not null,
	invoiceID int,
	CONSTRAINT FK_UsedUtilityUtilityID
	FOREIGN KEY (utilityTypeID)
	REFERENCES tbl_UtilityType(utilityTypeID),
	FOREIGN KEY (invoiceID)
	REFERENCES tbl_Invoice(invoiceID),
	CONSTRAINT FK_UsedUtilityRoom
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID)
)
GO

GO
Create table tbl_UsedService(
	usedServiceID int IDENTITY(1,1) PRIMARY KEY,
	roomID int  not null,
	startDate datetime not null,
	endDate datetime not null,
	servicetypeID int not null,
	usedQuantity int not null,
	price float  not null,
	invoiceID int,
	CONSTRAINT FK_ServiceServiceType
	FOREIGN KEY (servicetypeID)
	REFERENCES tbl_ServiceType(serviceTypeID),
	FOREIGN KEY (invoiceID)
	REFERENCES tbl_Invoice(invoiceID),
	CONSTRAINT FK_UsedServiceRoom
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID),
)
go

go
create table tbl_roomCharge(
	roomChargeID int IDENTITY(1,1) PRIMARY KEY,
	roomID int not null,
	startDate datetime not null,
	endDate datetime not null,
	invoiceID int,
	price int not null,
	
	CONSTRAINT FK_roomChargeInvoice
	FOREIGN KEY (invoiceID)
	REFERENCES tbl_Invoice(invoiceID),
	CONSTRAINT FK_roomCharge
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID),
)
go

create table tbl_Booking(
	bookingId int IDENTITY(1,1) PRIMARY KEY,
	userID int not null,
	roomID int not null,
	
	appointmentDate datetime not null, --ngày hẹn
	startDate datetime not null, -- thời gian book dự kiến
	endDate datetime not null, 

	--deposit float not null,

	isBookingAccecpt bit not null, -- chấp thuận ?
	bookingStatus bit not null,

	invoiceID int,

	CONSTRAINT FK_bookingInvoice
	FOREIGN KEY (invoiceID)
	REFERENCES tbl_Invoice(invoiceID),

	CONSTRAINT FK_bookingRoom
	FOREIGN KEY (roomID)
	REFERENCES tbl_Room(roomID),

	CONSTRAINT FK_bookingUser
	FOREIGN KEY (userID)
	REFERENCES tbl_Users(userID),
)
go

select * from tbl_Users 
update tbl_Users
set userStatus = 1