package pe.com.junioratoche.model.loanapplication.validation.rules.impl;

import pe.com.junioratoche.model.error.ErrorCode;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;
import java.util.function.Function;

public final class RequiredRule<T> implements Rule<T> {
    private final String field;
    private final Function<T, ?> getter;
    private final ErrorCode code;

    public RequiredRule(String field, Function<T, ?> getter, ErrorCode code) {
        this.field = field;
        this.getter = getter;
        this.code = code;
    }

    @Override
    public Flux<ErrorDetail> validate(T target) {
        Object value = getter.apply(target);
        boolean isMissing = (value == null) || (value instanceof String str && str.trim().isEmpty());
        if (isMissing) {
            return Flux.just(new ErrorDetail(code.code(), field, code.message()));
        }
        return Flux.empty();
    }
}
