package clone.slack.server.user.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class PasswordDeserializer extends JsonDeserializer<Password> {
    @Override
    public Password deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new Password(p.getText());
    }
}

class PasswordSerializer extends JsonSerializer<Password> {
    @Override
    public void serialize(Password password, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(password.getValue());
    }
}

@Getter
@JsonSerialize(using = PasswordSerializer.class)
@JsonDeserialize(using = PasswordDeserializer.class)
public class Password {
    private String value;

    public Password(String value) {
        this.value = sha256Encrypt(value);
    }

    public void setValue(String value) {
        this.value = sha256Encrypt(value);
    }

    private String sha256Encrypt(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}