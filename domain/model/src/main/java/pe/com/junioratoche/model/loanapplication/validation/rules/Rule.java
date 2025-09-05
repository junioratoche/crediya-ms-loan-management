package pe.com.junioratoche.model.loanapplication.validation.rules;

import pe.com.junioratoche.model.error.ErrorDetail;
import reactor.core.publisher.Flux;

public interface Rule<T> {
    Flux<ErrorDetail> validate(T target);
}
