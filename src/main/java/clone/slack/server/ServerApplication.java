package clone.slack.server;

import clone.slack.server.user.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

import static clone.slack.server.user.domain.UserInformation.Gender.MALE;
import static clone.slack.server.user.domain.UserInformation.StatusMessage.NONE;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
