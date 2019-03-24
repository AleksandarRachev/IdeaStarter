package ideastarter.ideastarter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class ImageUploadDto {
    @Column(nullable = false)
    private String imageUrl;
}
