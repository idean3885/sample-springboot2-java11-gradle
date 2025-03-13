# 샘플용 자바11 스프링부트2 프로젝트
> 자바 11, 스프링부트 2 그래들 빌드 샘플 프로젝트입니다.
> server port: 8080
> actuator port: 8081

# 1. 빌드 및 실행
## 1. 로컬 빌드 - 실행
### 1. build
```shell
./gradlew clean build
```

### 2. run jar
```shell
java -jar build/libs/sample-spring-boot-2-java-gradle-0.0.1-SNAPSHOT.jar
```

## 2. 도커 빌드 - 실행
### 1. docker build
```shell
docker build -t sample-spring-boot-2-java-gradle .
```

### 2. docker run
```shell
docker run -d -p 8080:8080 -p 8081:8081 sample-spring-boot-2-java-gradle
```

## 3. 도커 컴포즈 - 실행
### 1. 도커 컴포즈 실행
> 도커 컴포즈는 도커 이미지를 내려받아 실행합니다.
> 컴포즈 빌드없이 바로 실행할 경우, 최초 1회 관련 이미지를 받아오거나(도커 허브에서 풀) 이미지 1회 빌드합니다.
> 프로메테우스, 그라파나, MysQL 컨테이너 실행 완료 후 서버가 실행됩니다.
> 자세한 설명은 아래 링크를 참고하세요.
> [모니터링 도커 컴포즈](.docker/monitoring/README.md)
> [DB 도커 컴포즈](.docker/db/README.md)
```shell
docker-compose up
```

### 2. 도커 컴포즈 이미지 삭제
도커 컴포즈는 도커 이미지를 사용한 컨테이너 관리 도구이기 때문에 도커 이미지 자체에 대한 변경을 감지할 수 없습니다.
따라서 컴포즈에 포함된 도커 이미지가 변경되는 경우(스프링 부트 파일) 이미지를 삭제하고 다시 빌드해야 합니다.
```shell
docker-compose build --no-cache
```

# 2. 로그인
> 스프링 시큐리티가 적용되어있어 로그인이 필요합니다.
> 기본 계정은 아래와 같습니다.
```
// 어드민 계정 - ROLE_ADMIN
username: admin@email.com
password: admin1234
```
```
// 사용자 계정 - ROLE_USER
username: user@email.com
password: user1234
```

# 3. 개발 환경
## 1. 기술 스택
### 1. 서버
- Java 11 / SpringBoot2 -> 상세 내용 [build.gradle](build.gradle) 확인
- Spring security -> 인증/인가
- Spotless -> 코드 포맷팅, 빌드 시 코드 포맷 검사, [상세 보기](#99-참고)
- Actuator -> 모니터링 엔드포인트(+ 프로메테우스 메트릭)
- docker-compose -> 도커 컴포즈(모니터링, DB 와 함께 연동하기 위한 올인원 컴포즈)

### 2. 모니터링(via. docker-compose)
- Prometheus -> 모니터링 지표 수집, [모니터링 도커 컴포즈](.docker/monitoring/README.md)
- Grafana -> 모니터링 대시보드

### 3. DB(via. docker-compose)
- MySQL -> 데이터베이스, [DB 도커 컴포즈](.docker/db/README.md)

# 99. 참고
## 1. 코드 포맷팅 - spotless
- spotless 플러그인을 통해 빌드 과정에서 코드 포맷을 검사하고 통일할 수 있도록 설정함
