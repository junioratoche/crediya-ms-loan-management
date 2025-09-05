package pe.com.junioratoche.model.error;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class DomainErrorsTest {
    @Test
    void testAggregatedReturnsAggregatedException() {
        ErrorDetail error1 = new ErrorDetail("LOAN_001", "clientId", "clientId is required");
        ErrorDetail error2 = new ErrorDetail("LOAN_002", "identityDocument", "identityDocument is required");
        List<ErrorDetail> errors = List.of(error1, error2);
        Exception ex = DomainErrors.aggregated(errors);
        assertTrue(ex instanceof DomainErrors.Aggregated);
        DomainErrors.Aggregated aggEx = (DomainErrors.Aggregated) ex;
        assertEquals(errors, aggEx.getErrors());
        assertEquals(2, aggEx.getErrors().size());
        assertEquals("clientId", aggEx.getErrors().get(0).getField());
    }

    @Test
    void testAggregatedWithEmptyListThrowsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
            () -> DomainErrors.aggregated(List.of()));
        assertEquals("errors must not be empty", thrown.getMessage());
    }

    @Test
    void testOfMethods() {
        DomainErrors.Aggregated ex1 = DomainErrors.of(ErrorCode.LOAN_CLIENT_ID_REQUIRED);
        assertEquals(1, ex1.getErrors().size());
        assertEquals(DomainErrors.GLOBAL_FIELD, ex1.getErrors().get(0).getField());
        DomainErrors.Aggregated ex2 = DomainErrors.of(ErrorCode.LOAN_CLIENT_ID_REQUIRED, "custom message");
        assertEquals("custom message", ex2.getErrors().get(0).getMessage());
        DomainErrors.Aggregated ex3 = DomainErrors.ofField(ErrorCode.LOAN_CLIENT_ID_REQUIRED, "clientId");
        assertEquals("clientId", ex3.getErrors().get(0).getField());
        DomainErrors.Aggregated ex4 = DomainErrors.ofField(ErrorCode.LOAN_CLIENT_ID_REQUIRED, "clientId", "msg");
        assertEquals("msg", ex4.getErrors().get(0).getMessage());
        ErrorDetail detail = new ErrorDetail("CODE", "field", "msg");
        DomainErrors.Aggregated ex5 = DomainErrors.of(detail);
        assertEquals(detail, ex5.getErrors().get(0));
        DomainErrors.Aggregated ex6 = DomainErrors.of(detail, detail);
        assertEquals(2, ex6.getErrors().size());
    }

    @Test
    void testMerge() {
        ErrorDetail d1 = new ErrorDetail("C1", "f1", "m1");
        ErrorDetail d2 = new ErrorDetail("C2", "f2", "m2");
        ErrorDetail d3 = new ErrorDetail("C3", "f3", "m3");
        DomainErrors.Aggregated a1 = DomainErrors.of(d1);
        DomainErrors.Aggregated a2 = DomainErrors.of(d2);
        DomainErrors.Aggregated a3 = DomainErrors.of(d3);
        DomainErrors.Aggregated merged = DomainErrors.merge(a1, a2, a3);
        assertEquals(3, merged.getErrors().size());
        assertEquals("f1", merged.getErrors().get(0).getField());
        assertEquals("f2", merged.getErrors().get(1).getField());
        assertEquals("f3", merged.getErrors().get(2).getField());
    }

    @Test
    void testBuilder() {
        DomainErrors.Builder builder = DomainErrors.builder();
        assertTrue(builder.isEmpty());
        builder.addGlobal(ErrorCode.LOAN_CLIENT_ID_REQUIRED)
               .addField(ErrorCode.LOAN_IDENTITY_DOCUMENT_REQUIRED, "identityDocument")
               .addField(ErrorCode.LOAN_TYPE_ID_REQUIRED, "loanTypeId", "custom");
        assertFalse(builder.isEmpty());
        DomainErrors.Aggregated agg = builder.build();
        assertEquals(3, agg.getErrors().size());
        assertEquals(DomainErrors.GLOBAL_FIELD, agg.getErrors().get(0).getField());
        assertEquals("identityDocument", agg.getErrors().get(1).getField());
        assertEquals("custom", agg.getErrors().get(2).getMessage());
    }
}
