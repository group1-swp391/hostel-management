create database Hostel_Management
go
use Hostel_Management
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
go
select * from tbl_Users
go
CREATE TABLE tbl_Hostel(
	hostelID  int IDENTITY(1,1) PRIMARY KEY,
	ownerHostelID int NOT NULL,
	address varchar(255) not null,
	hostelName varchar(50) not null,
	room_quantity int not null,
	hostelImg image,
	hostelStatus bit not null,
	
	CONSTRAINT FK_userManage FOREIGN KEY (ownerHostelID) REFERENCES tbl_Users(userID)
)
go


	
go
Create table tbl_RoomType(
	typeID int IDENTITY(1,1) PRIMARY KEY,
	hostelID int NOT NULL,
	description varchar(200) not null,
	price float NOT NULL,
	depositPrice float not null,
	roomTImg image,
	roomName varchar (100) not null,
	roomTypeStatus bit not null, 

	CONSTRAINT FK_roomType
	FOREIGN KEY (hostelID)
	REFERENCES tbl_Hostel(hostelID),
)
GO


go
Create table tbl_Room(
	roomID int IDENTITY(1,1) PRIMARY KEY,
	roomNumber int NOT NULL,
	UserID int,
	typeID int NOT NULL,
	hostelID int NOT NULL,
	image image,
	roomStatus bit NOT NULL,
	FOREIGN KEY (hostelID)
	REFERENCES tbl_Hostel(hostelID),
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
	invoiceID int not null,
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
    appointmentDate datetime not null,
    startDate datetime not null,
    endDate datetime not null,
    isBookingAccept bit not null,
    depositPaymentStatus bit not null,
    invoiceID int,
    constraint FK_bookingInvoice FOREIGN KEY (invoiceID) REFERENCES tbl_Invoice(invoiceID),
    constraint FK_bookingRoom FOREIGN KEY (roomID) REFERENCES tbl_Room(roomID),
    constraint FK_bookingUser FOREIGN KEY (userID) REFERENCES tbl_Users(userID)
)

insert into tbl_UtilityType(utilityName, pricePerIndex, hostelID) VALUES('Dien', 3700, 1), ('Nuoc', 2600, 1), ('Dien', 3500, 3), ('Nuoc', 2300, 3);

GO
CREATE FUNCTION addPrice(@utilityTypeID INT)
RETURNS float AS
BEGIN
DECLARE @pricePerIndex float;
SET @pricePerIndex = (SELECT pricePerIndex FROM tbl_UtilityType WHERE utilityTypeID=@utilityTypeID);
RETURN @pricePerIndex;
END;
GO
insert into tbl_UsedUtility(roomID, startDate, endDate, utilityTypeID, oldIndex, newIndex, pricePerIndex) VALUES(1, '20220412', '20220512', 1, 2,3, dbo.addPrice(1));
insert into tbl_UsedUtility(roomID, startDate, endDate, utilityTypeID, oldIndex, newIndex, pricePerIndex) 
VALUES(1, '20220412', '20220512', 2, 4,5, dbo.addPrice(2)), (2, '20220314', '20220413', 3, 4,5, dbo.addPrice(3)), (2, '20220412', '20220512', 4, 2,5, dbo.addPrice(4));
SELECT dbo.addPrice(2);
SELECT * FROM tbl_UsedUtility;
SELECT * FROM tbl_UtilityType;

SELECT utilityTypeID FROM tbl_UtilityType WHERE utilityName LIKE 'Dien';
SELECT * FROM tbl_UsedUtility WHERE utilityTypeID IN (SELECT utilityTypeID FROM tbl_UtilityType WHERE utilityName LIKE 'Dien');
SELECT u.fullName, uti.* FROM tbl_Users u, tbl_UsedUtility uti, tbl_Room r
WHERE uti.roomID = 2 AND uti.roomID = r.roomID AND u.userID = r.UserID AND uti.utilityTypeID=3;
SELECT * FROM tbl_Users;
SELECT * FROM tbl_Room;