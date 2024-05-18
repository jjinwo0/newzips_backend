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

-- 상권정보 데이터 가공
DELETE FROM STORE
WHERE mainCategoryCode NOT IN ('P1', 'Q1', 'I2', 'R1')

-- 아파트명 인덱스 생성
ALTER TABLE houseinfo ADD FULLTEXT INDEX houseinfo_idx(apartmentName) WITH PARSER ngram;