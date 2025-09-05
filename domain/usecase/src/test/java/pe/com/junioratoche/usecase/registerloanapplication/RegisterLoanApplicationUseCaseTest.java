package pe.com.junioratoche.usecase.registerloanapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.gateways.LoanApplicationRepository;
import pe.com.junioratoche.model.loanapplication.validation.provider.LoanApplicationCreateRuleProvider;
import pe.com.junioratoche.model.loanapplication.validation.ReactiveValidator;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterLoanApplicationUseCaseTest {
    private LoanApplicationRepository repository;
    private LoanApplicationCreateRuleProvider ruleProvider;
    private ReactiveValidator validator;
    private RegisterLoanApplicationUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(LoanApplicationRepository.class);
        ruleProvider = mock(LoanApplicationCreateRuleProvider.class);
        validator = mock(ReactiveValidator.class);
        useCase = new RegisterLoanApplicationUseCase(repository, ruleProvider, validator);
    }

    @Test
    void testRegisterSuccess() {
        LoanApplication application = new LoanApplication();
        application.setClientId("123");
        when(validator.validate(application)).thenReturn(Mono.just(application));
        when(repository.save(any())).thenAnswer(invocation -> {
            LoanApplication app = invocation.getArgument(0);
            app.setId(UUID.fromString(UUID.randomUUID().toString()));
            return Mono.just(app);
        });

        LoanApplication result = useCase.register(application).block();
        assertNotNull(result);
        assertEquals("Pendiente de revisión", result.getStatus());
        assertNotNull(result.getRegistrationDate());
        assertNotNull(result.getId());
        verify(validator).validate(application);
        verify(repository).save(any());
    }

    @Test
    void testRegisterValidationError() {
        LoanApplication application = new LoanApplication();
        application.setClientId("123");
        when(validator.validate(application)).thenReturn(Mono.error(new RuntimeException("Error de validación")));

        Exception ex = assertThrows(RuntimeException.class, () -> useCase.register(application).block());
        assertEquals("Error de validación", ex.getMessage());
        verify(validator).validate(application);
        verify(repository, never()).save(any());
    }

    @Test
    void testRegisterRepositoryError() {
        LoanApplication application = new LoanApplication();
        application.setClientId("123");
        when(validator.validate(application)).thenReturn(Mono.just(application));
        when(repository.save(any())).thenReturn(Mono.error(new RuntimeException("Error al guardar")));

        Exception ex = assertThrows(RuntimeException.class, () -> useCase.register(application).block());
        assertEquals("Error al guardar", ex.getMessage());
        verify(validator).validate(application);
        verify(repository).save(any());
    }
}

