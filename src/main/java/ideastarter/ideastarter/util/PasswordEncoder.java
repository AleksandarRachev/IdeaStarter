package ideastarter.ideastarter.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

}
