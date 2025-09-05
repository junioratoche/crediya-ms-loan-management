package pe.com.junioratoche.model.loanapplication.validation;

import org.junit.jupiter.api.Test;
import pe.com.junioratoche.model.error.ErrorDetail;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReactiveValidatorTest {
    @Test
    void testValidateSuccess() {
        Rule<LoanApplication> rule = app -> Flux.empty();
        ReactiveValidator validator = new ReactiveValidator(List.of(rule));
        LoanApplication application = new LoanApplication();
        Mono<LoanApplication> result = validator.validate(application);
        assertEquals(application, result.block());
    }

    @Test
    void testValidateWithErrors() {
        ErrorDetail error = new ErrorDetail("CODE", "field", "msg");
        Rule<LoanApplication> rule = app -> Flux.just(error);
        ReactiveValidator validator = new ReactiveValidator(List.of(rule));
        LoanApplication application = new LoanApplication();
        Mono<LoanApplication> result = validator.validate(application);
        Exception ex = assertThrows(Exception.class, result::block);
        assertTrue(ex.getMessage().contains("Validation failed"));
    }
}
