package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.UserRepository;
import ideastarter.ideastarter.util.PasswordEncoder;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDao userDao;

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
    public SuccessMessage logoutUser(HttpSession session) throws NotLoggedException {
        validateLogin(session);
        session.invalidate();
        return new SuccessMessage("You logged out",LocalDate.now());
    }

    @GetMapping(value = "/profile")
    public ShowUserDto getUser(HttpSession session) throws NotLoggedException {
        validateLogin(session);
        User logged = (User) session.getAttribute("user");
        ShowUserDto user = new ShowUserDto();
        user.setId(logged.getId());
        user.setEmail(logged.getEmail());
        user.setFirstName(logged.getFirstName());
        user.setLastName(logged.getLastName());
        user.setImageUrl(logged.getImageUrl());
        return user;
    }

    @GetMapping(value = "/donates/{id}")
    public SuccessMessage getDonatesGatheres(@PathVariable("id") Long userId) throws SQLException {
        double moneyGathered = userDao.getTotalDonates(userId);
        BigDecimal bigDecimal = BigDecimal.valueOf(moneyGathered);
        return new SuccessMessage(""+bigDecimal,LocalDate.now());
    }

}
