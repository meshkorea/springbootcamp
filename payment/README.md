# payment

- 애플리케이션을 구동합니다
```shell
~/springbootcamp $ export SPRING_PROFILES_ACTIVE=local; export USER_TIMEZONE="Asia/Seoul"; ./gradlew :payment:clean :payment:bootRun -x :payment:clients:bootRun
$ curl -s http://localhost:10004/management/health
```


## 클라이언트 SDK 빌드 및 배포

```shell
~/springbootcamp $ ./gradlew :payment:clients:clean :payment::clients:publish
# 배포 결과는 https://nexus.mm.meshkorea.net/ 에서 확인할 수 있습니다
```

```shell
~/springbootcamp $ ./gradlew :payment:redoc
# 빌드된 API 문서는 build/redoc.html 입니다
```
