package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.UserRepository;
import ideastarter.ideastarter.util.PasswordEncoder;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/register")
    public SuccessMessage register(HttpSession session, HttpServletRequest request) throws BaseException {
        String email = request.getParameter("email");
        checkEmail(email);
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        if (email.isEmpty() || password.isEmpty() || password2.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            throw new MissingValuableFieldsException();
        }
        int count = this.userRepository.countUserByEmail(email);
        if (count > 0) {
            throw new UserExistsException();
        }
        if (!password.equals(password2)) {
            throw new PasswordsNotMatchingException();
        }
        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEmail(email);
        user.setPassword(PasswordEncoder.hashPassword(password));
        userRepository.save(user);
        request.getSession().setAttribute("user", user);
        session.setMaxInactiveInterval((60 * 60));
        return new SuccessMessage("Register successful", LocalDate.now());
    }

    @PostMapping(value = "/login")
    public ShowUserDto login(HttpSession session, HttpServletRequest request) throws BaseException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email.isEmpty() || password.isEmpty()) {
            throw new MissingValuableFieldsException();
        }
        int count = userRepository.countUserByEmail(email);
        User user = userRepository.findByEmail(email);
        if (count < 1 || !BCrypt.checkpw(password, user.getPassword())) {
            throw new WrongCredentialsException();
        }

        session.setMaxInactiveInterval(60 * 60);
        session.setAttribute("user", user);
        return new ShowUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getImageUrl());
    }

    private void checkEmail(String email) throws EmailInvalidFormatException {
        String emailRegex = "([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z]+)+)";
        if (!email.matches(emailRegex)) {
            throw new EmailInvalidFormatException();
        }
    }

    @PostMapping(value = "/logout")
    public String logoutUser(HttpSession session) throws NotLoggedException {
        validateLogin(session);
        session.invalidate();
        return "You logged out";
    }

}
