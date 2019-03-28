package ideastarter.ideastarter.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email",unique = true,nullable = false,length = 100)
    private String email;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "admin",nullable = false,columnDefinition = "boolean default false")
    private boolean admin;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

}
