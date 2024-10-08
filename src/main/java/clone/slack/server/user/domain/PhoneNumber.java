package clone.slack.server.user.domain;

import clone.slack.server.support.domain.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class PhoneNumber extends ValueObject<PhoneNumber> {
    private String number;

    public PhoneNumber(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("핸드폰 번호는 공백일 수 없습니다.");
        }
        if (!isValidPhoneNumber(number)) {
            throw new IllegalArgumentException("잘못된 형식의 핸드폰 번호입니다.");
        }
        this.number = number;
    }


    @SuppressWarnings("JpaAttributeTypeInspection")
    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{number};
    }

    @Override
    public String toString() {
        return number;
    }

    private boolean isValidPhoneNumber(String number) {
        return number.matches("^(010-?\\d{4}-?\\d{4})$");
    }
}