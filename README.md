# URL Shortening Service

## Key Concept
* 일반적인 URL을 입력받고, 해당 URL로 리다이렉트하는 단축 URL을 생성 하는 화면 제공
* table seq 로 요청 별 고유 값 생성
* 생성된 seq 를 base62 로 인코딩하여 short key를 생성
* 생성 요청 시 동일한 요청이 있는지 확인하여 중복 제거
* GET 으로 short key 가 요청 되었을 경우 key를 디코딩하여 seq 을 얻어 원본 요청 조회
* 조회된 원본 요청을 Http Response 의 301 , location 으로 생성하여 리다이렉션 유도
* 생성, 변환 시 각 카운트를 증가, 각 카운트에 대한 통계(TOP N)등 을 화면으로 제공



## 사용 기술
* SpringBoot
* Spring Data JPA
* H2 Database
* Thymeleaf
* JQuery
* Semantic ui (Fomantic ui)


## 실행 방법

* 요구 환경
    * JDK 8 이상

```shell
# 소스 다운로드
git clone https://github.com/ujacha/url-shortening.git
cd url-shortening

# 프로젝트 빌드
./mvnw clean package 
cd target

# 최초 실행 시 (데이터베이스 생성, 초기화)
java -jar -Dspring.profiles.active=init-db url-shortening-0.0.1-SNAPSHOT.jar

# 이후 실행 시 
java -jar url-shortening-0.0.1-SNAPSHOT.jar


```
* 브라우저에서 확인 
   * [http://localhost:8080](http://localhost:8080)

