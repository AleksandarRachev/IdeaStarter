package ideastarter.ideastarter.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class SuccessMessage {
    private String message;
    private LocalDate date;
}
