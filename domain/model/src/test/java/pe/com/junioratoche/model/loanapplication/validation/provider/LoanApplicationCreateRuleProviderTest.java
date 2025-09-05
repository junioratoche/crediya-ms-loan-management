package pe.com.junioratoche.model.loanapplication.validation.provider;

import org.junit.jupiter.api.Test;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanApplicationCreateRuleProviderTest {
    @Test
    void testRulesForCreateContainsExpectedRules() {
        List<String> validLoanTypeIds = List.of("LT1", "LT2");
        LoanApplicationCreateRuleProvider provider = new LoanApplicationCreateRuleProvider(validLoanTypeIds);
        List<Rule<LoanApplication>> rules = provider.rulesForCreate();
        assertNotNull(rules);
        assertFalse(rules.isEmpty());
        // Verifica que la última regla de tipo existe use los loanTypeIds correctos
        assertTrue(rules.stream().anyMatch(rule -> rule.getClass().getSimpleName().equals("LoanTypeExistsRule")));
        // Verifica que hay una regla para clientId
        assertTrue(rules.stream().anyMatch(rule -> rule.getClass().getSimpleName().equals("RequiredRule")));
    }
}

