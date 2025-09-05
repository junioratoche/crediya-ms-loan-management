package pe.com.junioratoche.model.loanapplication.validation.rules.impl;

import pe.com.junioratoche.model.error.ErrorCode;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.function.Function;

public final class RangeRule<T> implements Rule<T> {
    private final String field;
    private final Function<T, BigDecimal> getter;
    private final BigDecimal min;
    private final BigDecimal max;
    private final ErrorCode code;

    public RangeRule(String field, Function<T, BigDecimal> getter, String min, String max, ErrorCode code) {
        this.field = field;
        this.getter = getter;
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
        this.code = code;
    }

    @Override
    public Flux<ErrorDetail> validate(T target) {
        return Mono.justOrEmpty(getter.apply(target))
            .filter(value -> (min != null && value.compareTo(min) < 0) || (max != null && value.compareTo(max) > 0))
            .map(value -> new ErrorDetail(code.code(), field, code.message()))
            .flux();
    }
}
