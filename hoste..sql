USE [master]
GO
/****** Object:  Database [Hostel_Management]    Script Date: 7/19/2022 9:18:05 PM ******/
CREATE DATABASE [Hostel_Management]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Hostel_Management', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Hostel_Management.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Hostel_Management_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Hostel_Management_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Hostel_Management] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Hostel_Management].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Hostel_Management] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Hostel_Management] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Hostel_Management] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Hostel_Management] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Hostel_Management] SET ARITHABORT OFF 
GO
ALTER DATABASE [Hostel_Management] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Hostel_Management] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Hostel_Management] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Hostel_Management] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Hostel_Management] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Hostel_Management] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Hostel_Management] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Hostel_Management] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Hostel_Management] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Hostel_Management] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Hostel_Management] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Hostel_Management] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Hostel_Management] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Hostel_Management] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Hostel_Management] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Hostel_Management] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Hostel_Management] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Hostel_Management] SET RECOVERY FULL 
GO
ALTER DATABASE [Hostel_Management] SET  MULTI_USER 
GO
ALTER DATABASE [Hostel_Management] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Hostel_Management] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Hostel_Management] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Hostel_Management] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Hostel_Management] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Hostel_Management] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Hostel_Management', N'ON'
GO
ALTER DATABASE [Hostel_Management] SET QUERY_STORE = OFF
GO
USE [Hostel_Management]
GO
/****** Object:  Table [dbo].[tbl_Booking]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Booking](
	[bookingId] [int] IDENTITY(1,1) NOT NULL,
	[userID] [int] NULL,
	[roomID] [int] NOT NULL,
	[appointmentDate] [datetime] NOT NULL,
	[email] [varchar](50) NULL,
	[phone] [char](10) NULL,
PRIMARY KEY CLUSTERED 
(
	[bookingId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Contracts]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Contracts](
	[contractID] [int] IDENTITY(1,1) NOT NULL,
	[startDate] [datetime] NOT NULL,
	[endDate] [datetime] NOT NULL,
	[deposit] [float] NOT NULL,
	[userID] [int] NOT NULL,
	[roomID] [int] NOT NULL,
	[contractStatus] [bit] NOT NULL,
	[depositPaymentStatus] [bit] NULL,
	[createContractTime] [smalldatetime] NULL,
	[contractLiquidationTime] [smalldatetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[contractID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Hostel]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Hostel](
	[hostelID] [int] IDENTITY(1,1) NOT NULL,
	[ownerHostelID] [int] NOT NULL,
	[address] [nvarchar](255) NOT NULL,
	[hostelName] [nvarchar](50) NOT NULL,
	[hostelStatus] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[hostelID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Invoice]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Invoice](
	[invoiceID] [int] IDENTITY(1,1) NOT NULL,
	[roomID] [int] NOT NULL,
	[invoiceName] [nvarchar](50) NOT NULL,
	[totalAmount] [float] NOT NULL,
	[invoiceStatus] [bit] NOT NULL,
	[note] [nvarchar](255) NULL,
	[invoiceCreateDate] [smalldatetime] NULL,
	[paymentStatus] [bit] NOT NULL,
	[paymentDate] [smalldatetime] NULL,
	[userID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[invoiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Role]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Role](
	[roleID] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Room]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Room](
	[roomID] [int] IDENTITY(1,1) NOT NULL,
	[roomNumber] [int] NOT NULL,
	[UserID] [int] NULL,
	[typeID] [int] NOT NULL,
	[image] [image] NULL,
	[roomStatus] [bit] NOT NULL,
	[description] [text] NULL,
PRIMARY KEY CLUSTERED 
(
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [roomNumberAndType] UNIQUE NONCLUSTERED 
(
	[roomNumber] ASC,
	[typeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_roomCharge]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_roomCharge](
	[roomChargeID] [int] IDENTITY(1,1) NOT NULL,
	[roomID] [int] NOT NULL,
	[startDate] [datetime] NOT NULL,
	[endDate] [datetime] NOT NULL,
	[price] [float] NOT NULL,
	[invoiceID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[roomChargeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_RoomType]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_RoomType](
	[typeID] [int] IDENTITY(1,1) NOT NULL,
	[hostelID] [int] NOT NULL,
	[description] [text] NULL,
	[price] [float] NOT NULL,
	[depositPrice] [float] NOT NULL,
	[roomTImg] [image] NULL,
	[roomName] [nvarchar](100) NOT NULL,
	[roomTypeStatus] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[typeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_ServiceType]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_ServiceType](
	[serviceTypeID] [int] IDENTITY(1,1) NOT NULL,
	[serviceName] [nvarchar](50) NOT NULL,
	[price] [float] NULL,
	[hostelID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[serviceTypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UsedService]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UsedService](
	[usedServiceID] [int] IDENTITY(1,1) NOT NULL,
	[roomID] [int] NOT NULL,
	[startDate] [datetime] NOT NULL,
	[endDate] [datetime] NOT NULL,
	[servicetypeID] [int] NOT NULL,
	[usedQuantity] [int] NOT NULL,
	[price] [float] NOT NULL,
	[invoiceID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[usedServiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UsedUtility]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UsedUtility](
	[usedUtilityID] [int] IDENTITY(1,1) NOT NULL,
	[roomID] [int] NOT NULL,
	[startDate] [datetime] NOT NULL,
	[endDate] [datetime] NOT NULL,
	[utilityTypeID] [int] NOT NULL,
	[oldIndex] [int] NOT NULL,
	[newIndex] [int] NOT NULL,
	[pricePerIndex] [float] NOT NULL,
	[invoiceID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[usedUtilityID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_Users]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Users](
	[userID] [int] IDENTITY(1,1) NOT NULL,
	[userName] [varchar](20) NULL,
	[password] [varchar](20) NULL,
	[fullName] [nvarchar](50) NULL,
	[dateOfBirth] [date] NULL,
	[gender] [bit] NULL,
	[phone] [char](10) NULL,
	[email] [varchar](50) NULL,
	[documentID] [char](12) NULL,
	[documentFrontSide] [image] NULL,
	[documentBackSide] [image] NULL,
	[roleID] [int] NOT NULL,
	[userStatus] [bit] NOT NULL,
	[REGTIME] [smalldatetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[userName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[documentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UtilityType]    Script Date: 7/19/2022 9:18:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UtilityType](
	[utilityTypeID] [int] IDENTITY(1,1) NOT NULL,
	[utilityName] [nvarchar](50) NOT NULL,
	[pricePerIndex] [float] NULL,
	[hostelID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[utilityTypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbl_Contracts] ADD  DEFAULT (getdate()) FOR [createContractTime]
GO
ALTER TABLE [dbo].[tbl_Invoice] ADD  DEFAULT (getdate()) FOR [invoiceCreateDate]
GO
ALTER TABLE [dbo].[tbl_Users] ADD  DEFAULT (getdate()) FOR [REGTIME]
GO
ALTER TABLE [dbo].[tbl_Booking]  WITH CHECK ADD  CONSTRAINT [FK_bookingRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_Booking] CHECK CONSTRAINT [FK_bookingRoom]
GO
ALTER TABLE [dbo].[tbl_Booking]  WITH CHECK ADD  CONSTRAINT [FK_bookingUser] FOREIGN KEY([userID])
REFERENCES [dbo].[tbl_Users] ([userID])
GO
ALTER TABLE [dbo].[tbl_Booking] CHECK CONSTRAINT [FK_bookingUser]
GO
ALTER TABLE [dbo].[tbl_Contracts]  WITH CHECK ADD  CONSTRAINT [FK_contractRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_Contracts] CHECK CONSTRAINT [FK_contractRoom]
GO
ALTER TABLE [dbo].[tbl_Contracts]  WITH CHECK ADD  CONSTRAINT [FK_contractUser] FOREIGN KEY([userID])
REFERENCES [dbo].[tbl_Users] ([userID])
GO
ALTER TABLE [dbo].[tbl_Contracts] CHECK CONSTRAINT [FK_contractUser]
GO
ALTER TABLE [dbo].[tbl_Hostel]  WITH CHECK ADD  CONSTRAINT [FK_userManage] FOREIGN KEY([ownerHostelID])
REFERENCES [dbo].[tbl_Users] ([userID])
GO
ALTER TABLE [dbo].[tbl_Hostel] CHECK CONSTRAINT [FK_userManage]
GO
ALTER TABLE [dbo].[tbl_Invoice]  WITH CHECK ADD FOREIGN KEY([userID])
REFERENCES [dbo].[tbl_Users] ([userID])
GO
ALTER TABLE [dbo].[tbl_Invoice]  WITH CHECK ADD  CONSTRAINT [FK_InvoiceRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_Invoice] CHECK CONSTRAINT [FK_InvoiceRoom]
GO
ALTER TABLE [dbo].[tbl_Room]  WITH CHECK ADD  CONSTRAINT [FK_roomRoomType] FOREIGN KEY([typeID])
REFERENCES [dbo].[tbl_RoomType] ([typeID])
GO
ALTER TABLE [dbo].[tbl_Room] CHECK CONSTRAINT [FK_roomRoomType]
GO
ALTER TABLE [dbo].[tbl_Room]  WITH CHECK ADD  CONSTRAINT [FK_roomUser] FOREIGN KEY([UserID])
REFERENCES [dbo].[tbl_Users] ([userID])
GO
ALTER TABLE [dbo].[tbl_Room] CHECK CONSTRAINT [FK_roomUser]
GO
ALTER TABLE [dbo].[tbl_roomCharge]  WITH CHECK ADD  CONSTRAINT [FK_roomCharge] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_roomCharge] CHECK CONSTRAINT [FK_roomCharge]
GO
ALTER TABLE [dbo].[tbl_roomCharge]  WITH CHECK ADD  CONSTRAINT [FK_roomChargeInvoice] FOREIGN KEY([invoiceID])
REFERENCES [dbo].[tbl_Invoice] ([invoiceID])
GO
ALTER TABLE [dbo].[tbl_roomCharge] CHECK CONSTRAINT [FK_roomChargeInvoice]
GO
ALTER TABLE [dbo].[tbl_RoomType]  WITH CHECK ADD  CONSTRAINT [FK_roomType] FOREIGN KEY([hostelID])
REFERENCES [dbo].[tbl_Hostel] ([hostelID])
GO
ALTER TABLE [dbo].[tbl_RoomType] CHECK CONSTRAINT [FK_roomType]
GO
ALTER TABLE [dbo].[tbl_ServiceType]  WITH CHECK ADD  CONSTRAINT [FK_ServiceTypeHostelID] FOREIGN KEY([hostelID])
REFERENCES [dbo].[tbl_Hostel] ([hostelID])
GO
ALTER TABLE [dbo].[tbl_ServiceType] CHECK CONSTRAINT [FK_ServiceTypeHostelID]
GO
ALTER TABLE [dbo].[tbl_UsedService]  WITH CHECK ADD FOREIGN KEY([invoiceID])
REFERENCES [dbo].[tbl_Invoice] ([invoiceID])
GO
ALTER TABLE [dbo].[tbl_UsedService]  WITH CHECK ADD  CONSTRAINT [FK_ServiceServiceType] FOREIGN KEY([servicetypeID])
REFERENCES [dbo].[tbl_ServiceType] ([serviceTypeID])
GO
ALTER TABLE [dbo].[tbl_UsedService] CHECK CONSTRAINT [FK_ServiceServiceType]
GO
ALTER TABLE [dbo].[tbl_UsedService]  WITH CHECK ADD  CONSTRAINT [FK_UsedServiceRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_UsedService] CHECK CONSTRAINT [FK_UsedServiceRoom]
GO
ALTER TABLE [dbo].[tbl_UsedUtility]  WITH CHECK ADD FOREIGN KEY([invoiceID])
REFERENCES [dbo].[tbl_Invoice] ([invoiceID])
GO
ALTER TABLE [dbo].[tbl_UsedUtility]  WITH CHECK ADD  CONSTRAINT [FK_UsedUtilityRoom] FOREIGN KEY([roomID])
REFERENCES [dbo].[tbl_Room] ([roomID])
GO
ALTER TABLE [dbo].[tbl_UsedUtility] CHECK CONSTRAINT [FK_UsedUtilityRoom]
GO
ALTER TABLE [dbo].[tbl_UsedUtility]  WITH CHECK ADD  CONSTRAINT [FK_UsedUtilityUtilityID] FOREIGN KEY([utilityTypeID])
REFERENCES [dbo].[tbl_UtilityType] ([utilityTypeID])
GO
ALTER TABLE [dbo].[tbl_UsedUtility] CHECK CONSTRAINT [FK_UsedUtilityUtilityID]
GO
ALTER TABLE [dbo].[tbl_Users]  WITH CHECK ADD FOREIGN KEY([roleID])
REFERENCES [dbo].[tbl_Role] ([roleID])
GO
ALTER TABLE [dbo].[tbl_UtilityType]  WITH CHECK ADD  CONSTRAINT [FK_UtilityTypehostelID] FOREIGN KEY([hostelID])
REFERENCES [dbo].[tbl_Hostel] ([hostelID])
GO
ALTER TABLE [dbo].[tbl_UtilityType] CHECK CONSTRAINT [FK_UtilityTypehostelID]
GO
USE [master]
GO
ALTER DATABASE [Hostel_Management] SET  READ_WRITE 
GO
