# SI Cloud(Sun In Cloud)
![Logo](https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/b4ddd535-9744-439c-b9f9-412c8e149e6b)

**[Front-end Source Code Link](https://github.com/Sun-in-Cloud/SI-Cloud-Front-end)**

# 시연 영상
[Youtube Link](https://www.youtube.com/watch?v=L4Ca-xY4DGA)

# Skills
## Language & Framework
<img alt="Java" src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=white"/> <img alt="Spring Boot" src ="https://img.shields.io/badge/spring%20boot-6DB33F.svg?&style=for-the-badge&logo=springboot&logoColor=white"/>
## Database
<img alt="MariaDB" src ="https://img.shields.io/badge/MariaDB-003545.svg?&style=for-the-badge&logo=mariadb&logoColor=white"/>

## Environment
<img alt="eclipse" src ="https://img.shields.io/badge/Eclipse-525C86.svg?&style=for-the-badge&logo=eclipseide&logoColor=white"/> <img alt="jira" src ="https://img.shields.io/badge/jira-0052CC.svg?&style=for-the-badge&logo=jira&logoColor=white"/> <img alt="github" src ="https://img.shields.io/badge/github-181717.svg?&style=for-the-badge&logo=github&logoColor=white"/> <img alt="git" src ="https://img.shields.io/badge/git-F05032.svg?&style=for-the-badge&logo=git&logoColor=white"/>
## Communication
<img alt="notion" src ="https://img.shields.io/badge/notion-000000.svg?&style=for-the-badge&logo=notion&logoColor=white"/> <img alt="slack" src ="https://img.shields.io/badge/slack-4A154B.svg?&style=for-the-badge&logo=slack&logoColor=white"/>
<!-- * <img alt="" src =""/> -->

# 팀 소개
## Back-end
|[손준범](https://github.com/junbeom-Son)|[오은빈](https://github.com/svbean77)|[이진경](https://github.com/jingyeong0604)|
|---|---|---|
|<img src="https://github.com/OneTimeGroup/OneTimeTripCard/assets/89973303/af74cfa3-637d-4224-b98d-dcaa5a8bbcb6" width="150" height="200">|<img src="https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/478e488d-9104-448a-8537-d9758833c209" width="150" height="200">|<img src="https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/3c660cc3-0463-4edc-9061-1d9854b957eb" width="150" height="200">|
|백엔드 개발자|백엔드 개발자|백엔드 개발자|

## Front-end + 기획
|[노성은](https://github.com/seonggg)|[양유진](https://github.com/YYJ-1229)|[이솔](https://github.com/leessol)|
|---|---|---|
|<img src="https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/87373911/98fe7c7e-a4b4-4dba-b917-e009d606a2ed" width="150" height="200">|<img src="https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/f1b5aae4-1773-4f86-9b72-51365c347806" width="150" height="200">|<img src="https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/1b47590f-b134-4d39-9ead-f9bef53062a2" width="150" height="200">|
|프론트엔드 개발자|프론트엔드 개발자|기획자|

# 프로젝트 소개
**[API Sheet](https://sustaining-eel-c8a.notion.site/2f4d305a31a54ce5bea31020ae9d9f5c?v=75654fd58e884ae08f740661f811c143)** - API 정리 문서 Link
![image](https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/22bfd678-f895-4ccd-807c-c42123865675)

**[프로젝트 소개 PPT](https://www.miricanvas.com/v/12a4tlo)**

프로젝트 기간 - 2023-07-24 ~ 2023-08-17

## ERD
### System Tables
![siCloud_core_erd](https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/1700afa0-15d3-49bb-b477-7b00f81069b1)

### History Tables
![siCloud_history_erd](https://github.com/Sun-in-Cloud/SI-Cloud-Back-end/assets/89973303/a8da4370-af8f-4886-8007-36659fd2ff1f)


## 개요
3PL에서 기존에 사용하는 WMS를 추가로 개발하였습니다.
### 3PL이란?
***Third Party Logistics***의 약자로, 3자 물류 대행 서비스를 의미합니다. 
창고 대여, 재고 관리, 배송 등 물류와 관련된 업무를 대행해줘 판매자는 마케팅과 판매에만 집중할 수 있습니다.
### WMS란?
***Warehouse Management System***의 약자로 창고 관리 시스템을 의미합니다.
3PL에서 물류를 관리할 때 사용하는 시스템으로, 저희는 이 시스템을 개발하였습니다.

## 기본 기능
* 상품 등록
* 입고
* 출고
* 상품/입고/출고 목록 조회
* 상품/입고/출고 상세 조회
* 3PL과 판매자 검색 기능

## 마케팅
* 판매자 별 통계(최근 일주일간 일일 판매 건수, 매출, 당월/전월/전년 동월 판매 건수 및 매출, 올해/작년 판매 건수 및 매출)
* 상품 별 통계
* 상품 별 위험군 판단
* 판매 채널별 분석

## Fix
* 2023-08-24 DB에 저장된 컬럼명 seller_seller_no -> seller_no로 변경, 이에 따라 Entity 및 Repository Query 수정
* 2023-08-26 Swagger UI 사용을 위해 의존성 추가 및 Controller 수정
