package pe.com.junioratoche.model.loanapplication.validation.rules.impl;

import pe.com.junioratoche.model.error.ErrorCode;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

public class LoanTypeExistsRule<T> implements Rule<T> {
    private final String field;
    private final List<String> validLoanTypeIds;
    private final ErrorCode code;
    private final Function<T, String> getter;

    public LoanTypeExistsRule(String field, List<String> validLoanTypeIds, Function<T, String> getter, ErrorCode code) {
        this.field = field;
        this.validLoanTypeIds = validLoanTypeIds;
        this.getter = getter;
        this.code = code;
    }

    @Override
    public Flux<ErrorDetail> validate(T target) {
        String typeId = getter.apply(target);
        if (typeId == null || typeId.isBlank() || validLoanTypeIds.contains(typeId)) {
            return Flux.empty();
        }
        return Flux.just(new ErrorDetail(code.code(), field, "Invalid loan type"));
    }
}
