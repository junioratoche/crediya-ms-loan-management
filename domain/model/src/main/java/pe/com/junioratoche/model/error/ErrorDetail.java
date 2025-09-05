package pe.com.junioratoche.model.error;

import lombok.Value;

@Value
public class ErrorDetail {
    String code;
    String field;
    String message;
}
