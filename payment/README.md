# template

- 애플리케이션을 구동합니다
```shell
~/template $ export SPRING_PROFILES_ACTIVE=local; export USER_TIMEZONE="Asia/Seoul"; ./gradlew clean bootRun -x :clients:bootRun
$ curl -s http://localhost:8080/management/health
```


## 클라이언트 SDK 빌드 및 배포

```shell
~/template $ ./gradlew :template:clients:clean :template::clients:publish
# 배포 결과는 https://nexus.mm.meshkorea.net/ 에서 확인할 수 있습니다
```

```shell
~/template $ ./gradlew :template:redoc
# 빌드된 API 문서는 build/redoc.html 입니다
```
