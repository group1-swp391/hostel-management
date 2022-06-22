INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('admin','admin','Hung','2009-01-01',1,'0387788999','admin@hometro.com','9999',1,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('hukho','hunghung','Xuan Hung','2006-01-01',1,'0387788777','hukhho@gmail.com','123456789',2,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao','thao123','Phuong Thao','2009-01-01',1,'0987563269','thao2009@gmail.com','123000001',2,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao1','thao123','Phuong Thao1','2009-01-01',1,'0387788900','thao20091@gmail.com','123000002',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao2','thao123','Phuong Thao2','2009-01-01',1,'0387788901','thao20092@gmail.com','123000003',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao3','thao123','Phuong Thao3','2009-01-01',1,'0387788902','thao20093@gmail.com','123000004',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao4','thao123','Phuong Thao4','2009-01-01',1,'0387788903','thao20094@gmail.com','123000005',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao5','thao123','Phuong Thao5','2009-01-01',1,'0387788904','thao20095@gmail.com','123000006',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao6','thao123','Phuong Thao6','2009-01-01',1,'0387788906','thao20096@gmail.com','123000007',3,1)
INSERT INTO tbl_Users(userName,password,fullName,dateOfBirth,gender,phone,email,documentID,roleID,userStatus) values ('thao7','thao123','Phuong Thao7','2009-01-01',1,'0387788907','thao20097@gmail.com','123000008',3,1)
select * from tbl_Users

INSERT INTO tbl_Hostel(ownerHostelID,address,hostelName,hostelStatus) values (2,'123 Le Van Viet','Hung Hostel 123',1)
INSERT INTO tbl_Hostel(ownerHostelID,address,hostelName,hostelStatus) values (2,'120 Le Van Viet','Hung Hostel 120',1)
INSERT INTO tbl_Hostel(ownerHostelID,address,hostelName,hostelStatus) values (3,'2 Nguyen Van Tang','Thao Hostel 2',1)
select * from tbl_Hostel

go

INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (1,'phong 50m2', 5000000,3000000,'Phong vip 1',1)
INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (1,'phong 25m2', 3500000,2000000,'Phong vip 2',1)


INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (2,'phong 100m2', 7000000,5000000,'Phong kieu 1',1)
INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (2,'phong 75m2', 5000000,3500000,'Phong kieu 2',1)

INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (3,'phong co may lanh, tivi, day du noi that', 7500000,5000000,'Luxury 1',1)
INSERT INTO tbl_RoomType(hostelid,description,price,depositPrice,roomName,roomTypeStatus) values (3,'phong co tivi, bep, rem', 5500000,3500000,'Phong kieu 2',1)
select * from tbl_RoomType

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('006',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('007',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('008',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('009',1,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('010',1,1)


INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('006',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('007',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('008',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('009',2,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('010',2,1)

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',3,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',3,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',3,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',3,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',3,1)

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',4,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',4,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',4,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',4,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',4,1)

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',5,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',5,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',5,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',5,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',5,1)

INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('001',6,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('002',6,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('003',6,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('004',6,1)
INSERT INTO tbl_Room(roomNumber,typeID,roomStatus) values ('005',6,1)
select * from tbl_Room

select * from tbl_roomCharge
select * from tbl_RoomType
select * from tbl_UsedService
select * from tbl_UsedUtility
select * from tbl_roomCharge
select * from tbl_UsedService
select * from tbl_UsedUtility