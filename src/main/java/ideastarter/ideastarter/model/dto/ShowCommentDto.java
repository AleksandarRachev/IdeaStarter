package ideastarter.ideastarter.model.dto;

import ideastarter.ideastarter.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShowCommentDto {

    private Long id;
    private String comment;
    private Long postId;
    private ShowUserDto user;

}
