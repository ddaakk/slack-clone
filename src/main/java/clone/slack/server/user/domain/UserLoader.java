package clone.slack.server.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static clone.slack.server.user.domain.UserInformation.Gender.MALE;
import static clone.slack.server.user.domain.UserInformation.StatusMessage.NONE;

@Component
@RequiredArgsConstructor
public class UserLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public static User.UserBuilder aUser() {
        return User.builder()
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

    @Override
    public void run(String... args) throws Exception {
        User user = aUser().build();
        userRepository.add(user);
    }
}
