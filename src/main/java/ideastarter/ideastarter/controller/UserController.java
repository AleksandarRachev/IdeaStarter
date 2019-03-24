package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.dto.UserLoginDto;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.UserRepository;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController{

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/register")
    public SuccessMessage registerUser(@Valid @RequestBody User user, HttpSession session) throws MissingValuableFieldsException, UserExistsException {
        if(user.getAge()==null || user.getUsername()==null || user.getPassword()==null || user.getFirstName()==null || user.getLastName()==null){
            throw new MissingValuableFieldsException();
        }
        int count = this.userRepository.countUserByUsername(user.getUsername());
        if(count>0){
            throw new UserExistsException();
        }
        this.userRepository.save(user);
        session.setMaxInactiveInterval(60*60);
        session.setAttribute("user",user);
        return new SuccessMessage("Register successful", LocalDate.now());
    }

    @PostMapping(value = "/login")
    public ShowUserDto loginUser(@Valid @RequestBody UserLoginDto login, HttpSession session) throws BaseException {
        if(login.getUsername()==null || login.getPassword()==null||login.getPassword2()==null){
            throw new MissingValuableFieldsException();
        }
        if(!login.getPassword().equals(login.getPassword2())){
            throw new PasswordsNotMatchingException();
        }
        int count = userRepository.countUserByUsername(login.getUsername());
        User user = userRepository.findByUsername(login.getUsername());
        if(count<1 || !user.getPassword().equals(login.getPassword())){
            throw new WrongCredentialsException();
        }
        session.setMaxInactiveInterval(60*60);
        session.setAttribute("user",user);
        return new ShowUserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getAge(),user.getUsername(),user.getImageUrl());
    }

}
