package pe.com.junioratoche.model.loanapplication.gateways;

import pe.com.junioratoche.model.loanapplication.LoanApplication;
import reactor.core.publisher.Mono;

public interface LoanApplicationRepository {
    Mono<LoanApplication> save(LoanApplication application);
}
