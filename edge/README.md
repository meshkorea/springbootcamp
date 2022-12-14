# edge

## 클라이언트 SDK 빌드 및 배포

```shell
./gradlew :edge:webclient:clean :edge::webclient:publish
./gradlew :edge:resttemplate:clean :edge::resttemplate:publish
# 배포 결과는 https://nexus.mm.meshkorea.net/ 에서 확인할 수 있습니다
```

```shell
./gradlew :edge:redoc
# 빌드된 API 문서는 build/redoc.html 입니다
```
