package it.beata.ordermanager.items.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class StandardResponse {

    private String timestamp;
    private Integer status;
    private String error;
    private String path;
    private String message;

    public StandardResponse (HttpStatus httpStatus, String path, String message) {
        this.timestamp = (new Date()).toString();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = path;
        this.message = message;
    }

}
