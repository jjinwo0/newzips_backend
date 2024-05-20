-- 뉴집스 중요 쿼리 정리

use ssafyhome;

-- csv파일 가져오는 방법
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/gangjoo.csv'
INTO TABLE store
CHARACTER SET utf8
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

-- 상권정보 테이블
CREATE TABLE `ssafyhome`.`store` (
  `storeCode` VARCHAR(100) NOT NULL,
  `storeName` TEXT NULL,
  `mainCategoryCode` TEXT NULL,
  `mainCategoryName` TEXT NULL,
  `subCategoryCode` TEXT NULL,
  `subCategoryName` TEXT NULL,
  `dongCode` TEXT NULL,
  `dongName` TEXT NULL,
  `doro` TEXT NULL,
  `lng` TEXT NULL,
  `lat` TEXT NULL,
  PRIMARY KEY (`storeCode`));

-- 상권정보 데이터 가공 P1 : 교육, Q1 : 병원, I2 : 음식, R1 : 예술/스포츠
DELETE FROM STORE
WHERE mainCategoryCode NOT IN ('P1', 'Q1', 'I2', 'R1')

-- 아파트명 인덱스 생성
ALTER TABLE houseinfo ADD FULLTEXT INDEX houseinfo_idx(apartmentName) WITH PARSER ngram;

-- 뉴스 테이블
CREATE TABLE 'news' (
	'id' int NOT NULL AUTO_INCREAMENT,
    'newsDate' date DEFAULT NULL,
    'newsTitle' varchar(100) DEFAULT NULL,
    'newsLink' varchar(500) DEFAULT NULL,
    PRIMARY KEY('id')
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

use ssafyhome;
show tables;

drop table if exists members;

-- 멤버 테이블
create table members (
    member_id bigint primary key auto_increment,
    username varchar(200) not null,
    email varchar(300) not null,
    password varchar(100) not null default '1234',
    role varchar(10) not null,
    member_type varchar(20) not null,
    nickname varchar(100),
    profile varchar(250) not null,
    refresh_token varchar(500),
    refresh_token_expiration_time datetime(3)
);

insert into members(username, email, password, role, member_type, nickname, profile) values('testAdmin', 'admin@happyhouse.com', '1234', 'ADMIN', 'LOCAL', '관리자', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testAdmin');

-- 공지사항 테이블
CREATE TABLE board (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

