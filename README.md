<p>
  <div align="center">
    <img src="./docs/images/slack.png" width="50%"  alt="slack"/>
  </div>
</p>

<p align="center">
"Slack은 팀 협업과 커뮤니케이션을 위해 설계된 클라우드 기반의 메시징 플랫폼입니다."
<br>
Slack을 모티브로 만든 채팅 API 서버 토이 프로젝트입니다
</p>

<br>
<br>

## 💬 월 6500만명 이상 사용하고 있는 Slack은 어떻게 만들어진 것 일까요?

2024년 기준으로 슬랙(Slack)의 일일 활성 사용자(DAU)는 약 3,880만 명, 월간 활성 사용자(MAU)는 약 6,500만 명에 달합니다.

대규모 트래픽을 어떻게 처리하고 있을까요?  
대용량 데이터를 어떻게 다루고 있을까요?  
이러한 궁금증에서부터 직접 Slack API 서버를 구현해보는 프로젝트를 진행하게 되었습니다.

## 💬 단순히 Slack의 기능만 구현하지 않았습니다!

* 실제 Slack이 대규모 트래픽을 장애없이 어떻게 처리하고 있는지
* 채팅과 실시간 푸시 알람 서비스는 어떻게 구현하였는지
* 유지보수성을 위한 객체지향적 설계는 어떻게 이루어져야 하는지
* 냄새나는 코드를 제거해서 읽기 좋은 코드를 만들기 위해서는 어떻게해야 하는지

대용량 트래픽에도 장애없이 동작할 수 있도록 성능과 유지보수성을 고려한 서비스를 만들기 위해서, 읽기 좋은 코드 객체지향적 설계를 위해 노력하였습니다.

---

![빌드 상태](https://img.shields.io/github/actions/workflow/status/your-repo/build.yml?branch=main)
![라이선스](https://img.shields.io/github/license/your-repo/slack-clone)
![기여자 수](https://img.shields.io/github/contributors/your-repo/slack-clone)
![포크 수](https://img.shields.io/github/forks/your-repo/slack-clone)
![스타 수](https://img.shields.io/github/stars/your-repo/slack)

## 📚 목차

- [요구사항 분석](#-요구사항 분석)
- [기능 소개](#-기능-소개)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [ERD](#-ERD)
- [라이선스](#-라이선스)

## 📝 요구사항 분석

<details>
<summary>접기/펼치기</summary>

**사용자 역할 및 권한**

* 일반 사용자
    * 채널 참여 및 생성: 퍼블릭/프라이빗 채널 생성 및 참여
    * 메시지 전송: 텍스트, 파일, 이미지, 비디오, 코드 스니펫 전송
    * 알림 설정: 멘션, 키워드 알림 설정 가능
    * 파일 관리: 채팅 내 파일 업로드 및 관리, 클라우드 통합
    * 프로필 관리: 프로필 사진, 상태 메시지, 사용 언어 설정
    * 메시지 검색 및 북마크: 과거 메시지 검색 및 중요 메시지 북마크

* 관리자
    * 사용자 관리: 사용자 계정 생성, 수정, 삭제
    * 채널 관리: 채널 생성, 수정, 삭제 및 접근 권한 부여
    * 권한 관리: 관리자 권한 부여 및 회수
    * 감사 로그: 활동 내역 모니터링 및 로그 조회
    * 통계 및 보고서: 사용자 활동, 채널 활성도 통계 보고서 생성

* 게스트 사용자
    * 채널 참여: 초대받은 채널 참여
    * 메시지 전송 제한: 특정 채널에서만 메시지 전송 가능
    * 읽기 전용 채널: 읽기 전용 채널 접근 가능

---

**기능 요구사항**

* 채널 관리
    * 채널 유형: 퍼블릭/프라이빗 채널/읽기 전용 채널
    * 채널 검색: 이름, 설명, 최근 활동 기준 검색
    * 채널 내 스레드: 특정 메시지에 대한 스레드 생성
    * 채널 고정 메시지: 중요한 메시지를 상단에 고정
    * 채널 주제 및 설명 설정: 주제와 설명 추가

* 메세징 기능
    * 실시간 메시징: WebSocket을 이용한 실시간 메시지 송수신
    * 이모티콘 및 반응: 이모티콘으로 메시지 반응 추가
    * 멘션 기능: @username으로 특정 사용자 멘션
    * 코드 스니펫 공유: 코드 하이라이트 기능 지원
    * 파일 및 미디어 공유: 파일, 이미지, 비디오 업로드 및 공유(제한 100MB)
    * 메시지 편집 및 삭제: 메시지 편집 및 삭제 가능

---

**비기능 요구사항**

* 성능
    * 실시간 통신: 낮은 지연 시간으로 실시간 메시지 송수신 가능
    * 확장성: 사용자 및 채널 수 증가에도 성능 유지
    * 데이터베이스 성능: 대량 데이터 처리 및 검색 최적화

* 백업
    * mysqldump 혹은 Percona XtraBackup 사용 (비용 발생이 크지 않다면 RDS SnapShot으로 대체 가능)

* 모니터링
    * 분산 애플리케이션 환경에서 로그를 수집할 수 있는 환경 구성(ELK Stack, OpenSearch 등)
    * Prometheus, Grafana를 사용해 시스템의 상태를 모니터링하고 알람을 설정하며 데이터를 시각화

* 가용성 및 안정성
    * 99.9% 가용성 보장: 무중단 배포, 로드 밸런싱, 자동 복구
    * 백업 및 복구: 정기적인 백업 및 복구 절차 마련
    * 서버 장애 대응: 자동 복구 설계

</details>

## ✨ 기능 소개

- **실시간 메시징**: 즉각적인 메시지 전송으로 팀과 원활하게 소통하세요.
- **채널 관리**: 공개 및 비공개 채널을 생성하고 관리할 수 있습니다.
- **파일 공유**: 대화 중에 파일, 이미지, 비디오를 업로드하고 공유하세요.

## 🛠 기술 스택

- **프론트엔드**: React.js, Next.js
- **백엔드**: Spring Boot
- **실시간 통신**: WebSocket, Socket.IO
- **인증 및 보안**: OAuth 2.0, JWT, 2단계 인증(2FA)
- **데이터베이스**: MySQL
- **파일 스토리지**: AWS S3
- **배포**: Docker, Kubernetes, GitHub Actions

## 🏛 시스템 아키텍처

## 🗃 ERD

프로젝트의 데이터베이스 구조를 시각화한 ERD(엔터티 관계 다이어그램)는 아래와 같습니다.

## 📜 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.