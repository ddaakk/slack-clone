<p>
  <div align="center">
    <img src="images/slack.png" width="50%"  alt="slack"/>
  </div>
</p>

<p align="center">
"Slack은 팀 협업과 커뮤니케이션을 위해 설계된 클라우드 기반의 메시징 플랫폼입니다."
<br>
Slack을 모티브로 만든 채팅 API 서버 토이 프로젝트입니다
</p>

<br>
<br>

### 💬 월 6500만명 이상 사용하고 있는 Slack은 어떻게 만들어진 것 일까요?

2024년 기준으로 슬랙(Slack)의 일일 활성 사용자(DAU)는 약 3,880만 명, 월간 활성 사용자(MAU)는 약 6,500만 명에 달합니다.

대규모 트래픽을 어떻게 처리하고 있을까요?  
대용량 데이터를 어떻게 다루고 있을까요?  
이러한 궁금증에서부터 직접 Slack API 서버를 구현해보는 프로젝트를 진행하게 되었습니다.

### 💬 단순히 Slack의 기능만 구현하지 않았습니다!

* 실제 Slack이 대규모 트래픽을 장애없이 어떻게 처리하고 있는지
* 채팅과 실시간 푸시 알람 서비스는 어떻게 구현하였는지
* 유지보수성을 위한 객체지향적 설계는 어떻게 이루어져야 하는지
* 냄새나는 코드를 제거해서 읽기 좋은 코드를 만들기 위해서는 어떻게해야 하는지

대용량 트래픽에도 장애없이 동작할 수 있도록 성능과 유지보수성을 고려한 서비스를 만들기 위해서, 읽기 좋은 코드 객체지향적 설계를 위해 노력하였습니다.

---

![빌드 상태](https://img.shields.io/github/actions/workflow/status/your-repo/build.yml?branch=main
![포크 수](https://img.shields.io/github/forks/your-repo/slack-clone)
![스타 수](https://img.shields.io/github/stars/your-repo/slack)

## 📚 목차

- [문제 정의](#-문제-정의)
- [요구사항 분석](#-요구사항-분석)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)

## 문제 정의

### 배경
현재 대규모 트래픽을 효과적으로 처리할 수 있는 채팅 플랫폼이 필요합니다. 슬랙과 유사한 서비스를 개발하여 팀과 조직이 효율적으로 커뮤니케이션할 수 있도록 하고자 합니다. 대용량 트래픽을 처리하면서도 안정적이고 빠른 사용자 경험을 제공하는 것이 주요 과제입니다.

### 필수 조건
* 확장성: 대규모 유저 기반을 처리할 수 있어야 하며, 사용자 수의 증가에 따라 시스템이 유연하게 확장될 수 있어야 합니다.
* 고가용성: 24/7 동안 서비스가 중단되지 않고 지속적으로 운영되어야 합니다.
* 낮은 지연 시간: 실시간 커뮤니케이션 서비스의 특성상 메시지 전달 지연이 최소화되어야 합니다.
* 보안: 데이터 보호와 사용자 인증의 강화를 통해 보안을 유지해야 합니다.

### 목표
* 대용량 트래픽을 효율적으로 처리할 수 있는 슬랙 클론 사이트 개발
* 초당 수만 건의 메시지 처리
* 글로벌 유저를 대상으로 안정적인 서비스를 제공

### 목표가 아닌 것
* 목표가 아닌 것
* 슬랙과 동일한 모든 기능을 구현하는 것
* 완전히 새로운 기능을 개발하는 것 (기존의 메신저 시스템에 초점을 맞춤)
* 오프라인 상태에서의 메시징 기능 구현

### 평가
평가
* 성공 기준: 사용자가 경험하는 지연 시간, 시스템 가용성, 보안 사고의 발생 여부 등을 기준으로 평가
* 실패 기준: 서비스 중단 횟수, 메시지 전달 지연, 사용자 수 증가에 따른 성능 저하 발생 여부

## 📝 요구사항 분석

<details>
<summary>접기/펼치기</summary>
<br>

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

## 🛠 기술 스택

- **프론트엔드**: React.js, Next.js
- **백엔드**: Spring Boot
- **실시간 통신**: WebSocket, Socket.IO
- **인증 및 보안**: OAuth 2.0, JWT, 2단계 인증(2FA)
- **데이터베이스**: MySQL
- **파일 스토리지**: AWS S3
- **배포**: Docker, Kubernetes, GitHub Actions

## 🏛 시스템 아키텍처


## 🗣 유비쿼터스 언어

<details>
<summary>접기/펼치기</summary>
<br>

1. **User (사용자)**
  - **정의**: Slack 클론 사이트를 사용하는 개인을 의미합니다.
  - **유비쿼터스 언어**: "User"는 클라이언트 애플리케이션과 백엔드 시스템에서 동일하게 사용되며, 사용자 관리, 인증, 프로필 설정 등과 관련된 모든 부분에서 일관되게 사용합니다.

2. **Workspace (워크스페이스)**
  - **정의**: 특정 조직이나 팀이 협업하는 공간을 의미하며, 사용자는 하나 이상의 워크스페이스에 소속될 수 있습니다.
  - **유비쿼터스 언어**: "Workspace"는 워크스페이스 생성, 관리, 사용자 초대 등에서 일관되게 사용됩니다.

3. **Channel (채널)**
  - **정의**: 워크스페이스 내에서 특정 주제나 프로젝트에 대해 대화가 이루어지는 공간입니다. 퍼블릭 채널과 프라이빗 채널로 나뉩니다.
  - **유비쿼터스 언어**: "Channel"은 채널 생성, 메시지 전송, 멤버 관리 등과 관련된 모든 기능에서 일관되게 사용됩니다.

4. **Message (메시지)**
  - **정의**: 사용자가 채널 또는 다이렉트 메시지에서 주고받는 텍스트, 이미지, 파일 등의 내용을 의미합니다.
  - **유비쿼터스 언어**: "Message"는 메시지 전송, 편집, 삭제, 반응(이모티콘 반응) 등의 기능에서 일관되게 사용됩니다.

5. **Direct Message (다이렉트 메시지)**
  - **정의**: 특정 두 사용자 간의 1:1 대화를 의미합니다.
  - **유비쿼터스 언어**: "Direct Message"는 다이렉트 메시지 전송, 수신, 알림 등의 기능에서 동일하게 사용됩니다.

6. **Notification (알림)**
  - **정의**: 사용자가 채널에서 멘션되거나 중요한 활동이 있을 때 시스템에서 보내는 알림 메시지입니다.
  - **유비쿼터스 언어**: "Notification"은 알림 설정, 수신, 관리 등에서 일관되게 사용됩니다.

7. **Role (역할)**
  - **정의**: 사용자가 워크스페이스 또는 채널 내에서 가지는 권한과 책임을 정의하는 개념입니다.
  - **유비쿼터스 언어**: "Role"은 권한 관리, 역할 부여 및 변경, 접근 제어 등에서 동일하게 사용됩니다.

8. **File (파일)**
  - **정의**: 사용자가 채널 또는 다이렉트 메시지에서 공유하는 문서, 이미지, 동영상 등의 데이터를 의미합니다.
  - **유비쿼터스 언어**: "File"은 파일 업로드, 다운로드, 삭제, 공유 등과 관련된 모든 부분에서 동일하게 사용됩니다.

9. **Permissions (권한)**
  - **정의**: 사용자가 특정 기능이나 데이터에 접근할 수 있는 권한을 의미합니다.
  - **유비쿼터스 언어**: "Permissions"은 사용자의 권한 부여, 수정, 확인과 같은 모든 액션에서 일관되게 사용됩니다.

10. **Workspace Admin (워크스페이스 관리자)**
  - **정의**: 워크스페이스 내에서 사용자와 채널을 관리할 수 있는 권한을 가진 사용자를 의미합니다.
  - **유비쿼터스 언어**: "Workspace Admin"은 관리자 역할과 관련된 모든 기능(사용자 초대, 워크스페이스 설정 관리 등)에서 동일하게 사용됩니다.

</details>