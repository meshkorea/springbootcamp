# Spring Bootcamp 3-1

커머스 서비스를 개발합니다

## 개발 환경

- amazonaws corretto jdk 17을 사용합니다
```shell
$ brew install homebrew/cask-versions/corretto17 --cask
$ jenv add /Library/Java/JavaVirtualMachines/amazon-corretto-17.jdk/Contents/Home
$ jenv versions
```

- `docker/compose.yml` 파일을 열어 자신의 플랫폼에 맞도록 수정합니다
- 아래 명령으로 MySQL(3306), Kafka(9092), jhipster-uaa(9999) 등을 구동합니다
```shell
./gradlew composeUp
# To stop and delete the cluster, ./gradlew composeDown
```

### 계정

docker service|username|password
---|---|---
mysql|root|secret
kafka|admin|admin-secret
kafka|alice|alice-secret

## 개발

- java code는 [google style guide](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml)를 따릅니다 (hard wrap은 120까지 허용)
- 패키지 구조는 [육각형 구조](https://reflectoring.io/spring-hexagonal/)를 따릅니다

### API 문서

```shell
./gradlew :{project}:redoc

# Prerequisite: brew install node
./gradlew :{project}:generate
```

### 서버 코드 생성

```shell
./gradlew :{project}:openApiGenerate
```

### 클라이언트 라이브러리 빌드 및 발행

```shell
./gradlew :{project}:clients:clean :{project}:clients:publish
```

### 프로젝트 최신화

```shell
./gradlew :{project}:dependencyUpdates
```

## 전체 클러스터 빌드 및 구동

```shell
./gradlew jibDockerBuild
./gradlew composeUp
# MySQL 클라이언트에서 각 서비스별 DDL 적용
# see docker/init.sql
```
