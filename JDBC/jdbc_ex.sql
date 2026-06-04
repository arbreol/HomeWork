-- 계정 확인하기
SELECT user, host
FROM mysql.user
WHERE user = 'psh';

-- 계정이 없는 경우 생성하기
/* root 계정*/
CREATE USER 'psh'@'%' IDENTIFIED BY 'psh';

-- phs계정에 jdbc_ex에 모든 권한 부여
GRANT ALL PRIVILEGES ON jdbc_ex.* TO 'psh'@'%';
-- 권한 적용
FLUSH PRIVILEGES;


/* psh 계정 접속*/
-- jdbc_ex 데이터베이스 사용
USE jdbc_ex;

-- members 테이블이 존재하면 삭제
DROP TABLE IF EXISTS members;

-- members 테이블 생성
CREATE TABLE members (
                         no INT AUTO_INCREMENT PRIMARY KEY,
                         id VARCHAR(20) NOT NULL UNIQUE,
                         password VARCHAR(20) NOT NULL,
                         name VARCHAR(30) NOT NULL,
                         role VARCHAR(10) NOT NULL,
                         deleted_yn CHAR(1) DEFAULT 'N' NOT NULL
);

-- 샘플 데이터 추가
INSERT INTO members
VALUES (DEFAULT, 'jihoon', '1234', '박지훈', 'ADMIN', 'N');

INSERT INTO members
VALUES (DEFAULT, 'yoonjung', '1111', '고윤정', 'USER', 'N');

INSERT INTO members
VALUES (DEFAULT, 'minami', '2222', '미나미', 'USER', 'Y');

COMMIT;

-- 추가 확인
SELECT * FROM members;