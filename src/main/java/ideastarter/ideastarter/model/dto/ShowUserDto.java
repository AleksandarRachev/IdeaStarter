package ideastarter.ideastarter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShowUserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}
