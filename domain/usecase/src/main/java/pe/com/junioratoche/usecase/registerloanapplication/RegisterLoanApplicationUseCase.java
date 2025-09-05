package pe.com.junioratoche.usecase.registerloanapplication;

import lombok.RequiredArgsConstructor;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.gateways.LoanApplicationRepository;
import pe.com.junioratoche.model.loanapplication.validation.provider.LoanApplicationCreateRuleProvider;
import pe.com.junioratoche.model.loanapplication.validation.ReactiveValidator;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RegisterLoanApplicationUseCase {
    private final LoanApplicationRepository repository;
    private final LoanApplicationCreateRuleProvider ruleProvider;
    private final ReactiveValidator validator;

    public Mono<LoanApplication> register(LoanApplication application) {
        return validator.validate(application)
            .map(app -> {
                app.setStatus("Pendiente de revisión");
                app.setRegistrationDate(LocalDateTime.now());
                return app;
            })
            .flatMap(repository::save);
    }
}
