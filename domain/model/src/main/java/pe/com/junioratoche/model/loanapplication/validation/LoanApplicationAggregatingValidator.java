package pe.com.junioratoche.model.loanapplication.validation;

import pe.com.junioratoche.model.error.*;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class LoanApplicationAggregatingValidator {
    private static final Pattern DOC_PATTERN = Pattern.compile("^[A-Za-z0-9]{8,20}$");
    private final List<String> validLoanTypeIds;

    public LoanApplicationAggregatingValidator(List<String> validLoanTypeIds) {
        this.validLoanTypeIds = validLoanTypeIds;
    }

    public Mono<LoanApplication> validateForCreate(LoanApplication app) {
        List<Mono<ErrorDetail>> checks = List.of(
                required(app.getClientId(), "clientId", ErrorCode.LOAN_CLIENT_ID_REQUIRED),
                required(app.getIdentityDocument(), "identityDocument", ErrorCode.LOAN_IDENTITY_DOCUMENT_REQUIRED),
                docFormat(app.getIdentityDocument()),
                required(app.getRequestedAmount(), "requestedAmount", ErrorCode.LOAN_REQUESTED_AMOUNT_REQUIRED),
                amountRange(app.getRequestedAmount()),
                required(app.getTermMonths(), "termMonths", ErrorCode.LOAN_TERM_MONTHS_REQUIRED),
                termRange(app.getTermMonths()),
                required(app.getLoanTypeId(), "loanTypeId", ErrorCode.LOAN_TYPE_ID_REQUIRED),
                loanTypeValid(app.getLoanTypeId()),
                required(app.getStatus(), "status", ErrorCode.LOAN_STATUS_REQUIRED),
                required(app.getRegistrationDate(), "registrationDate", ErrorCode.LOAN_REGISTRATION_DATE_REQUIRED)
        );
        return Flux.merge(checks)
                .collectList()
                .flatMap(errors -> errors.isEmpty() ? Mono.just(app) : Mono.error(DomainErrors.aggregated(errors)));
    }

    private Mono<ErrorDetail> required(Object v, String field, ErrorCode code) {
        return (v == null || (v instanceof String s && s.trim().isEmpty()))
                ? Mono.just(new ErrorDetail(code.code(), field, code.message()))
                : Mono.empty();
    }

    private Mono<ErrorDetail> docFormat(String doc) {
        if (doc == null || doc.isBlank()) return Mono.empty();
        return DOC_PATTERN.matcher(doc).matches()
                ? Mono.empty()
                : Mono.just(new ErrorDetail(ErrorCode.LOAN_IDENTITY_DOCUMENT_SIZE.code(), "identityDocument", ErrorCode.LOAN_IDENTITY_DOCUMENT_SIZE.message()));
    }

    private Mono<ErrorDetail> amountRange(BigDecimal amount) {
        if (amount == null) return Mono.empty();
        boolean out = amount.compareTo(new BigDecimal("1")) < 0 || amount.compareTo(new BigDecimal("1000000")) > 0;
        return out
                ? Mono.just(new ErrorDetail(ErrorCode.LOAN_REQUESTED_AMOUNT_OUT_OF_RANGE.code(), "requestedAmount", ErrorCode.LOAN_REQUESTED_AMOUNT_OUT_OF_RANGE.message()))
                : Mono.empty();
    }

    private Mono<ErrorDetail> termRange(Integer term) {
        if (term == null) return Mono.empty();
        boolean out = term < 1 || term > 120;
        return out
                ? Mono.just(new ErrorDetail(ErrorCode.LOAN_TERM_MONTHS_OUT_OF_RANGE.code(), "termMonths", ErrorCode.LOAN_TERM_MONTHS_OUT_OF_RANGE.message()))
                : Mono.empty();
    }

    private Mono<ErrorDetail> loanTypeValid(String typeId) {
        if (typeId == null || typeId.isBlank()) return Mono.empty();
        return validLoanTypeIds.contains(typeId)
                ? Mono.empty()
                : Mono.just(new ErrorDetail(ErrorCode.LOAN_TYPE_ID_REQUIRED.code(), "loanTypeId", "Invalid loan type"));
    }
}

