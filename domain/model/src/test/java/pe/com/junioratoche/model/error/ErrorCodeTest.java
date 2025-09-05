package pe.com.junioratoche.model.error;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {
    @Test
    void testErrorCodeProperties() {
        ErrorCode code = ErrorCode.LOAN_CLIENT_ID_REQUIRED;
        assertEquals("LOAN_001", code.code());
        assertEquals(400, code.statusCode());
        assertEquals("clientId is required", code.message());
    }

    @Test
    void testErrorCodeEnumEquality() {
        assertEquals(ErrorCode.LOAN_CLIENT_ID_REQUIRED, ErrorCode.LOAN_CLIENT_ID_REQUIRED);
        assertNotEquals(ErrorCode.LOAN_CLIENT_ID_REQUIRED, ErrorCode.LOAN_TYPE_ID_REQUIRED);
    }
}
