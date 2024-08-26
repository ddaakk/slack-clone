package clone.slack.server.user.domain;

import clone.slack.server.support.domain.AggregateRoot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;
import clone.slack.server.user.domain.UserId.UserIdJavaType;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;


@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends AggregateRoot<User, UserId> {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JavaType(UserIdJavaType.class)
    private UserId id;


    @Embedded
    private UserInformation information;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    private Password password;
}
