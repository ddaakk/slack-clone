package clone.slack.server.user.domain;

import jakarta.persistence.*;
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

    @Column(name = "STATUS_MESSAGE")
    @Enumerated(EnumType.STRING)
    private StatusMessage statusMessage;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "PHONE_NUMBER")
    @Embedded
    private PhoneNumber phoneNumber;
}

@Getter
enum StatusMessage {
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

@Getter
enum Gender {
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