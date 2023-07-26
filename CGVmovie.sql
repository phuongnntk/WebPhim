create database CGVmovie;

create table [user](
	id int primary key identity,
	username varchar(50) unique not null,
	password varchar(10) not null,
	email varchar(50) unique not null,
	isAdmin bit not null default 0,
	isActive bit not null default 1
);

create table video(
	id int primary key identity,
	title nvarchar(100) not null,
	href  nvarchar(150) unique not null,
	poster varchar(255) null,
	[views] int not null default 0,
	shares int not null default 0,
	description nvarchar(255) not null,
	isActive bit not null default 1
);

create table history(
	id int primary key identity,
	userid int foreign key references [user](id),
	videoid int foreign key references [video](id), 
	viewedDate  datetime not null default getdate(),
	isLiked bit not null default 0,
	likedDate datetime null
);

Insert into [user](username,password,email, isAdmin) values
('PhuongNTK','123','Nguyen020392@gmail.com',1),
('TrucTT','123','TrucTTPham@gmail.com',0),
('VanPL','123','PhungLongVan@gmail.com',1),
('BaoGV','123','Giabao@gmail.com',0)

Insert into video(title, href, description) values
 (N'RESIDENT EVIL: ĐẢO TỬ THẦN trailer - KC: 07.07.2023','pXCg0UUWEF0',N'RESIDENT EVIL: ĐẢO TỬ THẦN Trailer'),
 (N'MA SƠ TRỤC QUỶ - BEST CUT NHÀ NGUYỆN ÁM NGUYỀN | KC: 30.06.2023','OUjXDTY6h4U',N'MA SƠ TRỤC QUỶ Trailer'),
 (N'NHIỆM VỤ: BẤT KHẢ THI - NGHIỆP BÁO trailer - KC: 07.07.2023','8ZT-SaPx0GQ',N'NHIỆM VỤ: BẤT KHẢ THI - NGHIỆP BÁO Trailer'),
 (N'PHIM ĐIỆN ẢNH THÁM TỬ LỪNG DANH CONAN:TÀU NGẦM SẮT MÀU ĐEN','jna3dhoDNQ8',N'THÁM TỬ LỪNG DANH CONAN Trailer'),
 (N'Bỗng Dưng Được Yêu - KC: 07.07.2023','SNN8XaBbNd0',N'Bỗng Dưng Được Yêu Trailer'),
 (N'RUBY THỦY QUÁI TUỔI TEEN | TVC Teenager trailer - KC: 07.07.2023',N'pgu8nxCvvho','RUBY THỦY QUÁI TUỔI TEEN Trailer'),
 (N'Livestream trailer - KC: 30.06.2023','aFrqX5oXsA4',N'Livestream Trailer'),
 (N'Cuộc Hẹn Của Carl - Phim ngắn - KC: 23.06.2023','HGTLsjkmuvE',N'Trailer Cuộc Hẹn Của Carl-Phim ngắn chiếu trước ELEMENTAL')

Insert into history(userid, videoid, isLiked, likedDate) values
(1,1,1, getDate()),
(2,3,0, null)