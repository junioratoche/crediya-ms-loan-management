package pe.com.junioratoche.model.loanapplication.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pe.com.junioratoche.model.error.ErrorCode;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
    ErrorCode code();
    String field() default "";
}
