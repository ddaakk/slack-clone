package clone.slack.server.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
public class UserInformation {

    //TODO 프로필 사진
    //TODO 자기소개

    @Column(name = "E_MAIL")
    private String email;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "PHONE_NUMBER")
    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "STATUS_MESSAGE")
    @Enumerated(EnumType.STRING)
    private StatusMessage statusMessage;

    @Builder
    public UserInformation(String email, Gender gender, LocalDate birthday, PhoneNumber phoneNumber, StatusMessage statusMessage) {
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.statusMessage = statusMessage;
    }

    public UserInformation() {

    }

    @Getter
    public enum Gender {
        MALE("남성"),
        FEMALE("여성");

        private final String displayName;

        Gender(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    @Getter
    public enum StatusMessage {
        ONLINE("Online"),
        AWAY("Away"),
        DO_NOT_DISTURB("Do Not Disturb"),
        OFFLINE("Offline"),
        BUSY("Busy"),
        NONE("None");

        private final String displayName;

        StatusMessage(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}