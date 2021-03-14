# URL Shortening Service

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

# 브라우저에서 확인 ([http://localhost:8080](http://localhost:8080))

```


