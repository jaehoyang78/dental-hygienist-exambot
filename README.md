# 오늘의 치과위생사

치과위생사 국가시험 대비용 Android + 웹 학습 앱입니다. 기존 `exambot`의 웹 미리보기/WebView/학습 알림 구조를 분리해 만든 새 프로젝트입니다.

## v1 기능

- 매일 과목 혼합 10문제
- 15개 영역, 초기 30문항
- 즉시 채점과 핵심 해설
- 오답노트와 과목별 정답률
- 문항별 출처 유형·검증일·원문 동일 여부 표시
- 원하는 시간의 Android 학습 알림
- 하나의 HTML/JS 코드로 GitHub Pages 미리보기와 APK 화면 유지

## 콘텐츠 원칙

초기 문항은 치과위생사 국가시험의 공개 출제범위와 빈출 개념을 바탕으로 새로 작성한 **기출 경향 재구성 문항**입니다. 공식 기출 원문이라고 표시하지 않습니다. 실제 공개 기출을 추가할 때는 연도, 회차, 문항번호, 공식 URL, 사용 가능 범위와 정답 검증을 함께 기록합니다.

## 미리보기와 빌드

`index.html`을 열면 바로 미리볼 수 있습니다. 수정 후 `bash scripts/sync-web.sh`를 실행하면 `docs/`와 Android assets가 함께 갱신됩니다.

GitHub Actions의 **Build Android APK**를 실행하면 설치용 `app-debug.apk`가 Artifacts에 생성됩니다. 로컬에서는 JDK 17과 Gradle 8.9로 `gradle assembleDebug`를 실행합니다.
