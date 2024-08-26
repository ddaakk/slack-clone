package clone.slack.server;

import clone.slack.server.user.domain.*;

import java.time.LocalDate;

import static clone.slack.server.user.domain.UserInformation.Gender.MALE;
import static clone.slack.server.user.domain.UserInformation.StatusMessage.NONE;

public class Fixtures {
    public static UserId USER_ID = new UserId(1L);

    public static User.UserBuilder aUser() {
        return User.builder()
                .id(USER_ID)
                .password(new Password("1234"))
                .information(
                        anUserInformation().build()
                );
    }

    public static UserInformation.UserInformationBuilder anUserInformation() {
        return UserInformation.builder()
                .email("user@test.com")
                .gender(MALE)
                .birthday(LocalDate.of(2000, 1, 1))
                .phoneNumber(new PhoneNumber("010-1234-5678"))
                .statusMessage(NONE);
    }
}
