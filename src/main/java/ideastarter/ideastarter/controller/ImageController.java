package ideastarter.ideastarter.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.ImageMissingException;
import ideastarter.ideastarter.util.exception.NotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;

@RestController
public class ImageController extends BaseController {
    private static final String IMAGE_PATH = "C:\\Users\\rache\\Desktop\\users\\";

    @Autowired
    private UserDao userDao;
    private ObjectMapper objectMapper = new ObjectMapper();

    //    @PostMapping("/images")
//    public SuccessMessage userImageUpload(@PathVariable(value = "imageUrl") String image,HttpSession session) throws SQLException, NotLoggedException, IOException {
//        validateLogin(session);
//        User user = (User)session.getAttribute("user");
//        String base64 = image;
//        byte[] bytes = Base64.getDecoder().decode(base64);
//        String name = user.getId()+System.currentTimeMillis()+".png";
//        File file = new File(IMAGE_PATH+name);
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(bytes);
//        this.userDao.addImageUrl(name,user.getId());
//        fos.close();
//        return new SuccessMessage("Image uploaded", LocalDate.now());
//    }
    @PostMapping("/images")
    public SuccessMessage userImageUpload(@RequestBody String input, HttpSession session) throws SQLException, NotLoggedException, IOException {
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        JsonNode jsonNode = objectMapper.readTree(input);
        String base64 = jsonNode.get("imageUrl").textValue();
        byte[] bytes = Base64.getDecoder().decode(base64);
        String name = user.getId() + System.currentTimeMillis() + ".png";
        File file = new File(IMAGE_PATH + name);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        this.userDao.addImageUrl(IMAGE_PATH, name, user.getId());
        fos.close();
        return new SuccessMessage("Image uploaded", LocalDate.now());
    }

    @GetMapping(value = "/images/users/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadImage(@PathVariable("id") long userId) throws Exception {
        String imageUrl = this.userDao.getImageUrl(userId);
        if (imageUrl == null) {
            throw new ImageMissingException();
        }
        File image = new File(IMAGE_PATH + imageUrl);
        FileInputStream fis = new FileInputStream(image);
        return fis.readAllBytes();
    }

    @DeleteMapping(value = "/images")
    public SuccessMessage deleteImage(HttpSession session) throws ImageMissingException, SQLException, NotLoggedException {
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        userDao.deleteImage(IMAGE_PATH, user);
        return new SuccessMessage("Image deleted", LocalDate.now());
    }

}
