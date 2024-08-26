package clone.slack.server.user.persistence;

import clone.slack.server.support.jpa.hibernate.BaseRepository;
import clone.slack.server.user.domain.User;
import clone.slack.server.user.domain.UserId;
import clone.slack.server.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
class DefaultUserRepository extends BaseRepository<User, UserId, UserJpaRepository> implements UserRepository {
    public DefaultUserRepository(UserJpaRepository repository) {
        super(repository);
    }
}