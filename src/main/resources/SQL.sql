-- 뉴집스 중요 쿼리 정리

use ssafyhome;

-- csv파일 가져오는 방법
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/gwangjoo.csv'
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

-- 회원 테이블
create table members (
                         member_id bigint primary key auto_increment,
                         username varchar(200) not null,
                         email varchar(300) not null,
                         password varchar(100) not null default '1234',
                         role varchar(10) not null,
                         member_type varchar(20) not null,
                         nickname varchar(100),
                         profile varchar(250) not null,
                         price int,
                         refresh_token varchar(500),
                         refresh_token_expiration_time datetime(3)
);

insert into members(username, email, password, role, member_type, nickname, profile, price) values('testAdmin', 'admin@happyhouse.com', '1234', 'ADMIN', 'LOCAL', '관리자', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testAdmin', null);
insert into members(username, email, password, role, member_type, nickname, profile, price) values('testExpert', 'admin@happyhouse.com', '1234', 'EXPERT', 'LOCAL', '박프로', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testExpert', 10000);
insert into members(username, email, password, role, member_type, nickname, profile, price) values('testExpert2', 'admin@happyhouse.com', '1234', 'EXPERT', 'LOCAL', '김프로', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testExpert2', 40000);
insert into members(username, email, password, role, member_type, nickname, profile, price) values('testExpert3', 'admin@happyhouse.com', '1234', 'EXPERT', 'LOCAL', '최프로', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testExpert3', 70000);
insert into members(username, email, password, role, member_type, nickname, profile, price) values('testExpert4', 'admin@happyhouse.com', '1234', 'EXPERT', 'LOCAL', '황프로', 'https://api.dicebear.com/8.x/pixel-art/svg?seed=testExpert4', 90000);

update members set price=100 where member_id=5;

select * from members;

drop table if exists room;

-- 채팅방 테이블
create table room (
                      room_id bigint primary key auto_increment,
                      status varchar(20),
                      entired_time datetime(3),
                      expert_id bigint,
                      member_id bigint,
                      foreign key (expert_id) references members(member_id),
                      foreign key (member_id) references members(member_id)
);

select * from room;

insert into room(status, entired_time, expert_id, member_id) values('LIVE', DATE_ADD(NOW(), INTERVAL 60 MINUTE), 2, 1);
insert into room(status, entired_time, expert_id, member_id) values('LIVE', DATE_ADD(NOW(), INTERVAL 60 MINUTE));
insert into room(status, entired_time, expert_id, member_id) values('LIVE', DATE_ADD(NOW(), INTERVAL 60 MINUTE));
insert into room(status, entired_time, expert_id, member_id) values('LIVE', DATE_ADD(NOW(), INTERVAL 60 MINUTE));
insert into room(status, entired_time, expert_id, member_id) values('LIVE', DATE_ADD(NOW(), INTERVAL 60 MINUTE));

drop event update_status_if_expired;

-- 채팅방 Status 업데이트 이벤트
CREATE EVENT update_status_if_expired
ON SCHEDULE EVERY 1 MINUTE
DO
UPDATE SSAFYHOME.ROOM
SET status = 'EXPIRED'
WHERE entired_time < NOW();


-- 공지사항 테이블
CREATE TABLE board (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 뉴스레터
CREATE TABLE `newsletter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `recipients` text,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `sendDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `isSend` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


