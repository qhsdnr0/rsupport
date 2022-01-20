![header](https://capsule-render.vercel.app/api?type=Slice&color=auto&height=350&section=header&text=%20%20RSupport&fontSize=90&textBg=true)

# RSupport REST API 구현 과제



> ## [Assignment] 과제 제출 기업정보



- 기업명 : RSupport
- [RSupport 웹사이트 링크](http://www.rsupport.com/)

<br>

> ## 사용 기술



  ![Java](https://img.shields.io/badge/Java-3670A0?style=for-the-badge&logo=java&logoColor=ffdd54)ㅤ![Springboot](https://img.shields.io/badge/springboot-%23092E20.svg?style=for-the-badge&logo=springboot&logoColor=white)ㅤ![MySQL](https://img.shields.io/badge/MySQL-%2307405e.svg?style=for-the-badge&logo=MySQL&logoColor=white)ㅤ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)ㅤ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)ㅤ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

> ## 과제 내용



ㅤ아래 요구사항에 맞춰 공지사항 관리 Restful API를 개발합니다.



### ▶︎ 기능 요구사항

- 공지사항 등록, 수정, 삭제, 조회 API를 구현한다.
- 공지사항 등록시 입력 항목은 다음과 같다
	- 제목, 내용, 공지 시작일시, 공지 종료일시, 첨부파일 (여러개)
- 공지사항 조회시 응답은 다음과 같다.
  - 구현 방법과 이유에 대한 간략한 설명



### ▶︎ 비기능 요구사항
- REST API로 구현.
- 개발 언어는 Java, Kotlin 중 익숙한 개발 언어로 한다.
- 웹 프레임 워크는 Spring Boot 을 사용한다.
- Persistence 프레임 워크는 Hibernate 사용시 가산점
- 데이터 베이스는 제약 없음
- 기능 및 제약사항에 대한 단위/통합테스트 작성
- 대용량 트래픽을 고려하여 구현할 것
- 핵심 문제해결 전략 및 실행 방법등을 README 파일에 명시



<br>

> ## 모델링





![image](https://user-images.githubusercontent.com/80999321/149992102-e40ce8d7-a95a-4bb7-bbe7-39c42bf7b273.png)


<br>

> ## 구현 기능 

### ▶︎ 사용자 생성 API

- `/users`를 통해 사용자를 생성하며, 별도의 구현과제에 포함된 사항은 아니라 단순 이름만으로 사용자를 생성할 수 있도록 했습니다.

  


### ▶︎ 공지사항 등록, 조회, 삭제, 수정 API 

- Post     `/notices`를 통해 공지사항을 등록하며 등록 성공시 `CREATED` 메시지를 리턴합니다.
- Get      `/notices/{noticeId}`로 공지사항을 조회합니다. API가 호출될 때마다 조회수가 1씩 증가합니다.
- Delete `/notices/{noticeId}`로 공지사항을 삭제합니다.
- Patch   `/notices/{noticeId}`로 공지사항을 수정합니다.



### ▶︎ UnitTest

- Junit5를 활용하여 Service의 UnitTest 코드를 작성했습니다.
- 터미널에서 ```./gradlew test'``` 명령어로 테스트를 할 수 있습니다.



### ▶︎ 대용량 트래픽을 고려하여 추가한 내용
- 캐싱 기능을 활용하기 위한 Redis 서버 사용
```
본 프로젝트에서 대량의 트래픽은 공지사항 조회에서 발생할 가능성이 가장 높다고 판단했습니다.
따라서 조회기능을 효율적으로 사용하기 위한 캐싱기능을 활용하는 것으로 결정했습니다.
조회 시 공지사항 데이터를 Redis서버에 저장
삭제 or 수정 시 Redis서버에 저장된 데이터 삭제

최초 조회시 해당 데이터를 Redis서버에 저장합니다. 
이후 동일 공지사항 조회는 Redis서버에 저장된 데이터를 불러오는 방식으로 매우 빠르며 실제 DB hit역시 줄것으로 판단했습니다.
```
- 캐시를 활용하지 않았을 때의 속도<br>
![image](https://user-images.githubusercontent.com/80999321/149992489-b11214fb-f6ed-4351-bbd5-1737f40c0667.png)
 
- 캐시를 활용했을 때의 속도<br>
![image](https://user-images.githubusercontent.com/80999321/149992502-9f5d6fea-0912-4a06-b637-16aced8515ea.png)

### ▶︎ 실제 파일첨부 기능 미구현
- 파일첨부 기능을 활용하기 위해서는 AWS S3를 이용해서 별도의 파일 저장소를 확보해야하지만 프리티어 요금제가 별도로 존재하지 않는 관계로 첨부파일 주소만 DB에 저장하는 방식으로 구현했습니다.
- 실제로 이 기능을 구현한다면 S3와 연동할 클래스를 추가하여 S3에는 해당 파일이 등록되도록, DB에는 해당 파일 주소가 등록되도록 구현할 수 있겠습니다.






<br>


> ## API Document & Test 

#### 프로젝트 실행 방법
1. 해당 프로젝트를 clone하고, 프로젝트 폴더로 이동한다.
```
git clone https://github.com/qhsdnr0/rsupport.git
cd rsupport
```

<br>
2. src/main/resources와 src/test/resources에 application.properties파일을 추가한다.

```
// src/main/resources/application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url={localhost 또는 RDS 주소}:{port 번호}/{DB명}?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul

spring.datasource.username={계정명}

spring.datasource.password={비밀번호}

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.format_sql=true

spring.redis.host=localhost

spring.redis.port=6379

spring.jackson.serialization.fail-on-empty-beans=false


// src/test/resources/application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url={localhost 또는 RDS 주소}:{port 번호}/{test_DB명}?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul

spring.datasource.username={계정명}

spring.datasource.password={비밀번호}

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto=create-drop

spring.jpa.properties.hibernate.format_sql=true
```

<br>
3. 캐시 사용을 위한 Redis를 설치한다.

```
sudo apt-get install redis
```

<br>
4. 프로젝트 폴더에서 ./gradlew build 명령어를 통해 빌드한다.
<br>
5. java -jar build/libs/rsupport-0.0.1-SNAPSHOT.jar 명령어를 통해 프로젝트를 실행한다.

#### API 문서

1. [Postman API 문서 링크](https://documenter.getpostman.com/view/18218753/UVXnFZZw)로 접속해 우측 상단의 `Run in Postman` 버튼을 클릭합니다.
2. 개인 Workspace로 Import 합니다.
3. hostname 환경변수를 deploy로 선택합니다.
4. 배포 주소 `3.34.123.56:8080` 를 확인합니다. 
5. API 문서 예시를 참고해 Request를 보냅니다.



<br>

> ## 폴더 구조 



```
rsupport
├── build
│   ├── bootJarMainClassName
│   ├── classes
│   │   └── java
│   │       ├── main
│   │       │   └── rsupport
│   │       │       └── rsupport
│   │       │           ├── config
│   │       │           │   └── RedisConfig.class
│   │       │           ├── controller
│   │       │           │   ├── NoticeController.class
│   │       │           │   ├── NoticeForm.class
│   │       │           │   ├── UserController.class
│   │       │           │   └── UserForm.class
│   │       │           ├── domain
│   │       │           │   ├── File.class
│   │       │           │   ├── Notice.class
│   │       │           │   └── User.class
│   │       │           ├── exception
│   │       │           │   └── BadRequestException.class
│   │       │           ├── repository
│   │       │           │   ├── FileQueryRepository.class
│   │       │           │   ├── FileRepository.class
│   │       │           │   ├── NoticeRepository.class
│   │       │           │   └── UserRepository.class
│   │       │           ├── RsupportApplication.class
│   │       │           └── service
│   │       │               ├── NoticeService.class
│   │       │               └── UserService.class
│   │       └── test
│   │           └── rsupport
│   │               └── rsupport
│   │                   ├── RsupportApplicationTests.class
│   │                   └── service
│   │                       ├── NoticeServiceTest.class
│   │                       └── UserServiceTest.class
│   ├── generated
│   │   └── sources
│   │       ├── annotationProcessor
│   │       │   └── java
│   │       │       ├── main
│   │       │       └── test
│   │       └── headers
│   │           └── java
│   │               ├── main
│   │               └── test
│   ├── libs
│   │   ├── nohup.out
│   │   ├── rsupport-0.0.1-SNAPSHOT.jar
│   │   └── rsupport-0.0.1-SNAPSHOT-plain.jar
│   ├── reports
│   │   └── tests
│   │       └── test
│   │           ├── classes
│   │           │   ├── rsupport.rsupport.RsupportApplicationTests.html
│   │           │   ├── rsupport.rsupport.service.NoticeServiceTest.html
│   │           │   └── rsupport.rsupport.service.UserServiceTest.html
│   │           ├── css
│   │           │   ├── base-style.css
│   │           │   └── style.css
│   │           ├── index.html
│   │           ├── js
│   │           │   └── report.js
│   │           └── packages
│   │               ├── rsupport.rsupport.html
│   │               └── rsupport.rsupport.service.html
│   ├── resources
│   │   ├── main
│   │   │   └── application.properties
│   │   └── test
│   │       └── application.properties
│   ├── test-results
│   │   └── test
│   │       ├── binary
│   │       │   ├── output.bin
│   │       │   ├── output.bin.idx
│   │       │   └── results.bin
│   │       ├── TEST-rsupport.rsupport.RsupportApplicationTests.xml
│   │       ├── TEST-rsupport.rsupport.service.NoticeServiceTest.xml
│   │       └── TEST-rsupport.rsupport.service.UserServiceTest.xml
│   └── tmp
│       ├── bootJar
│       │   └── MANIFEST.MF
│       ├── compileJava
│       │   └── previous-compilation-data.bin
│       ├── compileTestJava
│       │   └── previous-compilation-data.bin
│       ├── jar
│       │   └── MANIFEST.MF
│       └── test
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── nohup.out
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── rsupport
    │   │       └── rsupport
    │   │           ├── config
    │   │           │   └── RedisConfig.java
    │   │           ├── controller
    │   │           │   ├── NoticeController.java
    │   │           │   ├── NoticeForm.java
    │   │           │   ├── UserController.java
    │   │           │   └── UserForm.java
    │   │           ├── domain
    │   │           │   ├── File.java
    │   │           │   ├── Notice.java
    │   │           │   └── User.java
    │   │           ├── exception
    │   │           │   └── BadRequestException.java
    │   │           ├── repository
    │   │           │   ├── FileQueryRepository.java
    │   │           │   ├── FileRepository.java
    │   │           │   ├── NoticeRepository.java
    │   │           │   └── UserRepository.java
    │   │           ├── RsupportApplication.java
    │   │           └── service
    │   │               ├── NoticeService.java
    │   │               └── UserService.java
    │   └── resources
    │       └── application.properties
    └── test
        ├── java
        │   └── rsupport
        │       └── rsupport
        │           ├── RsupportApplicationTests.java
        │           └── service
        │               ├── NoticeServiceTest.java
        │               └── UserServiceTest.java
        └── resources
            └── application.properties
```





# Reference

이 프로젝트는 RSupport에서 출제한 과제를 기반으로 만들었습니다.
