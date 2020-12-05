# maven pom.xml에 있는 디펜던시 다운로드

mvn clean install -U

-U means force update of dependencies

# 어노테이션

@Entity : 이 클래스를 테이블과 매핑한다고 JPA에게 알림

@Table : 엔티티 클래스에 매핑할 테이블 정보를 알려준다.  name
속성을 사용해서 Member 엔티티를 MEMEBER 테이블에 매핑
이 어노테이션을 생략하면은 기본적으로 클래스 이름을 테이블 이름으로 매핑한다
(정확히는 엔티티 이름을 사용)

@Id : 엔티티 클래스의 필드를 기본 키(Primary Key) 에 매핑한다. 이렇게 된 멤버를 식별자 필드라고 한다

@Column : 필드를 컬럼에 매핑한다. name속성을 이용

@매핑정보가 없는 필드 없으면 필드명을 컬럼명으로 매핑한다. mysql 대소문자 구분하던가? 한번 해보고 아무튼 그 기본으로 필드명으로 매핑이다.


# 순서
## mysql 테이블 생성
## persistence.xml 작성
## 테이블에 해당하는 회원클래스 Member 생성
## Member클래스에 어노테이션 추가 javax.persistence껄로 해보자



# DB설정
-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        8.0.22-0ubuntu0.20.04.2 - (Ubuntu)
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- l_hibernate 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `l_hibernate` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `l_hibernate`;

-- 테이블 l_hibernate.MEMBER 구조 내보내기
CREATE TABLE IF NOT EXISTS `MEMBER` (
  `ID` varchar(50) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `AGE` int NOT NULL,
  PRIMARY KEY (`ID`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
