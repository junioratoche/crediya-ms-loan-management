package pe.com.junioratoche.model.loanapplication.validation;

import org.junit.jupiter.api.Test;
import pe.com.junioratoche.model.loanapplication.LoanApplication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationAggregatingValidatorTest {
    @Test
    void testValidateAggregatesErrors() {
        LoanApplicationAggregatingValidator validator = new LoanApplicationAggregatingValidator(List.of("LT1", "LT2"));
        LoanApplication application = new LoanApplication();
        // No se setean campos requeridos, debe fallar
        Exception ex = assertThrows(Exception.class, () -> validator.validateForCreate(application).block());
        assertTrue(ex.getMessage().contains("Validation failed"));
    }

    @Test
    void testValidateNoErrors() {
        LoanApplicationAggregatingValidator validator = new LoanApplicationAggregatingValidator(List.of("LT1", "LT2"));
        LoanApplication application = new LoanApplication();
        application.setClientId("C1");
        application.setIdentityDocument("ABC12345");
        application.setRequestedAmount(new java.math.BigDecimal("1000"));
        application.setTermMonths(12);
        application.setLoanTypeId("LT1");
        application.setStatus("PENDIENTE");
        application.setRegistrationDate(java.time.LocalDateTime.now());
        assertEquals(application, validator.validateForCreate(application).block());
    }
}
