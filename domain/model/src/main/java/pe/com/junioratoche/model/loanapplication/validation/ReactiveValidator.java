package pe.com.junioratoche.model.loanapplication.validation;

import lombok.RequiredArgsConstructor;
import pe.com.junioratoche.model.error.DomainErrors;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Validador reactivo para entidades LoanApplication usando reglas personalizadas.
 */
@RequiredArgsConstructor
public final class ReactiveValidator {
    private final List<Rule<LoanApplication>> rules;

    /**
     * Valida el objeto LoanApplication usando las reglas configuradas.
     *
     * @param target objeto LoanApplication a validar
     * @return Mono<LoanApplication> con el objeto validado o error agregado
     */
    public Mono<LoanApplication> validate(final LoanApplication target) {
        Flux<ErrorDetail> errorFlux = Flux.fromIterable(rules)
            .flatMap(rule -> rule.validate(target));
        return errorFlux
            .collectList()
            .flatMap(errors -> errors.isEmpty()
                ? Mono.just(target)
                : Mono.error(DomainErrors.aggregated(errors)));
    }
}
