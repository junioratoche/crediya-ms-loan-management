package pe.com.junioratoche.model.error;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DomainExceptionTest {
    @Test
    void testDomainExceptionWithMessage() {
        DomainException ex = new DomainException("E001", "Mensaje de error", 400);
        assertEquals("E001", ex.getCode());
        assertEquals("Mensaje de error", ex.getMessage());
        assertEquals(400, ex.getStatusCode());
        assertNull(ex.getMetadata());
    }

    @Test
    void testDomainExceptionWithMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("key", "value");
        DomainException ex = new DomainException("E002", "Error con metadata", 500, metadata);
        assertEquals("E002", ex.getCode());
        assertEquals("Error con metadata", ex.getMessage());
        assertEquals(500, ex.getStatusCode());
        assertEquals(metadata, ex.getMetadata());
    }
}
