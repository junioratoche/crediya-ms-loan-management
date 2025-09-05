package pe.com.junioratoche.model.loanapplication.validation.rules.impl;

import pe.com.junioratoche.model.error.ErrorCode;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.regex.Pattern;

public class IdentityDocumentRule<T> implements Rule<T> {
    private final String field;
    private final ErrorCode code;
    private final Function<T, String> getter;
    private static final Pattern DOC_PATTERN = Pattern.compile("^[A-Za-z0-9]{8,20}$");

    public IdentityDocumentRule(String field, Function<T, String> getter, ErrorCode code) {
        this.field = field;
        this.getter = getter;
        this.code = code;
    }

    @Override
    public Flux<ErrorDetail> validate(T target) {
        String doc = getter.apply(target);
        if (doc == null || doc.isBlank() || DOC_PATTERN.matcher(doc).matches()) {
            return Flux.empty();
        }
        return Flux.just(new ErrorDetail(code.code(), field, code.message()));
    }
}
