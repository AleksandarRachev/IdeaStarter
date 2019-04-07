package ideastarter.ideastarter.model.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post_updates")
public class PostUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "update_notes",nullable = false)
    private String updateNotes;
    @Column(name = "post_date",nullable = false)
    private Date postDate;
    @ManyToOne
    private Post post;
    @OneToOne
    private User user;

}
