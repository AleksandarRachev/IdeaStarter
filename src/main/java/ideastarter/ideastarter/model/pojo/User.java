package ideastarter.ideastarter.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean admin;
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

}
