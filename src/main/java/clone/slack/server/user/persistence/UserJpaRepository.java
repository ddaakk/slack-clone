package clone.slack.server.user.persistence;

import clone.slack.server.user.domain.User;
import clone.slack.server.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, UserId> {
}