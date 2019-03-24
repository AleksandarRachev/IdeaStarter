package ideastarter.ideastarter.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

    private String message;
    private int status;
    private LocalDate date;

}