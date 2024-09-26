# SheepEz_loan

# Project Set-Up

## Git Hook
프리 커밋 설정으로 커밋 전 코드 포맷 검사

```
$ git config core.hookspath .githooks
```

## IntelliJ IDEA

- 테스트 설정

```
// Gradle Build and run with IntelliJ IDEA
Build, Execution, Deployment > Build Tools > Gradle > Run tests using > IntelliJ IDEA	
```

# Lint

수동으로 코드 포맷 수정

```bash
./gradlew format
```
