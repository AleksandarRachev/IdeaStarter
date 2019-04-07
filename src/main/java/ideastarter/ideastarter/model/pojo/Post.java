package ideastarter.ideastarter.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",unique = true,nullable = false)
    private String title;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date endDate;
    @Column(name = "donates")
    private Double donates;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post")
    private List<PostUpdate> postUpdates;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToOne
    private Category category;

}
