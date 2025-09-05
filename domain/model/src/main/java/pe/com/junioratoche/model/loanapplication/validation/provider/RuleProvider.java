package pe.com.junioratoche.model.loanapplication.validation.provider;

import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;

import java.util.List;

public interface RuleProvider<T> {
    List<Rule<T>> rulesForCreate();
}
