# docker compose를 활용한 개발 환경 구축 (Part 1/2)
> 본 저장소는 docker compose를 활용한 개발 환경 구축 Part 1/2의 샘플 소스입니다.
- [gmarket tech blog - docker compose를 활용한 개발 환경 구축 Part 1](https://dev.gmarket.com/72)
- [gmarket tech blog - docker compose를 활용한 개발 환경 구축 Part 2](https://dev.gmarket.com/80)

## Re/Build Applications
```shell
docker compose up builder
```

## Run Applications
```shell
docker compose up api subscriber nginx redis
```


## 확인

- subscribing

```bash
curl "http://sub.gmarket.co.kr/subscribe/mytopic"
```

- publishing
```bash
curl "http://pub.gmarket.co.kr/publish/mytopic?payload=hello"
```