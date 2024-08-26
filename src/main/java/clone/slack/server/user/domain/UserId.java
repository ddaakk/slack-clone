package clone.slack.server.user.domain;

import clone.slack.server.support.domain.LongTypeIdentifier;
import clone.slack.server.support.jpa.hibernate.LongTypeIdentifierJavaType;

public class UserId extends LongTypeIdentifier {
    public UserId(Long id) {
        super(id);
    }

    public static class UserIdJavaType extends LongTypeIdentifierJavaType<UserId> {
        public UserIdJavaType() {
            super(UserId.class);
        }
    }
}