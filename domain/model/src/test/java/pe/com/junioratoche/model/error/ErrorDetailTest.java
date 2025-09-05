package pe.com.junioratoche.model.error;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorDetailTest {
    @Test
    void testErrorDetailProperties() {
        ErrorDetail detail = new ErrorDetail("E001", "field1", "Campo inválido");
        assertEquals("E001", detail.getCode());
        assertEquals("field1", detail.getField());
        assertEquals("Campo inválido", detail.getMessage());
    }

    @Test
    void testErrorDetailEqualsAndHashCode() {
        ErrorDetail d1 = new ErrorDetail("E001", "field1", "msg");
        ErrorDetail d2 = new ErrorDetail("E001", "field1", "msg");
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
