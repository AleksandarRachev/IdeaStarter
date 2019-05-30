package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.PostDao;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.ImageMissingException;
import ideastarter.ideastarter.util.exception.NotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/images")
public class ImageController extends BaseController {
    private static final String IMAGE_PATH = "C:\\Users\\Aleksandar_Rachev\\Desktop\\images\\";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @PostMapping
    public SuccessMessage userImageUpload(@RequestPart(value = "image") MultipartFile file, HttpSession session) throws SQLException, NotLoggedException, IOException {
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        String name = user.getId() + System.currentTimeMillis() + ".png";
        File newImage = new File(IMAGE_PATH+name);
        file.transferTo(newImage);
        userDao.addImageUrl(IMAGE_PATH,name,user.getId());
        user.setImageUrl(name);
        return new SuccessMessage("Image uploaded", LocalDate.now());
    }

    @PostMapping(value = "/posts/{id}")
    public SuccessMessage postImageUpload(@PathVariable("id")Long postId,@RequestPart(value = "image") MultipartFile file) throws SQLException, IOException {
        String name = postId + System.currentTimeMillis() + ".png";
        File newImage = new File(IMAGE_PATH+name);
        file.transferTo(newImage);
        userDao.addImageUrl(IMAGE_PATH,name,postId);
        return new SuccessMessage("Image uploaded", LocalDate.now());
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadImageById(@PathVariable("id") long userId) throws Exception {
        String imageUrl = this.userDao.getImageUrl(userId);
        if (imageUrl == null) {
            throw new ImageMissingException();
        }
        File image = new File(IMAGE_PATH + imageUrl);
        FileInputStream fis = new FileInputStream(image);
        return fis.readAllBytes();
    }

    @GetMapping(value = "/posts/{url}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] downloadImageByName(@PathVariable("url") String imageUrl) throws Exception {
        if (imageUrl == null) {
            throw new ImageMissingException();
        }
        File image = new File(IMAGE_PATH + imageUrl);
        FileInputStream fis = new FileInputStream(image);
        return fis.readAllBytes();
    }

    @DeleteMapping
    public SuccessMessage deleteImage(HttpSession session) throws ImageMissingException, SQLException, NotLoggedException {
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        userDao.deleteImage(IMAGE_PATH, user);
        return new SuccessMessage("Image deleted", LocalDate.now());
    }

}
