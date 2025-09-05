package pe.com.junioratoche.model.loanapplication.validation.provider;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import pe.com.junioratoche.model.error.ErrorCode;
import pe.com.junioratoche.model.loanapplication.LoanApplication;
import pe.com.junioratoche.model.loanapplication.validation.rules.Rule;
import pe.com.junioratoche.model.loanapplication.validation.rules.impl.RequiredRule;
import pe.com.junioratoche.model.loanapplication.validation.rules.impl.RangeRule;
import pe.com.junioratoche.model.loanapplication.validation.rules.impl.IdentityDocumentRule;
import pe.com.junioratoche.model.loanapplication.validation.rules.impl.LoanTypeExistsRule;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static pe.com.junioratoche.model.loanapplication.validation.provider.LoanApplicationValidationConstants.*;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class LoanApplicationCreateRuleProvider implements RuleProvider<LoanApplication> {
    List<String> validLoanTypeIds;

    @Override
    public List<Rule<LoanApplication>> rulesForCreate() {
        return List.of(
            new RequiredRule<>(CLIENT_ID_FIELD, LoanApplication::getClientId, ErrorCode.LOAN_CLIENT_ID_REQUIRED),
            new RequiredRule<>(IDENTITY_DOCUMENT_FIELD, LoanApplication::getIdentityDocument, ErrorCode.LOAN_IDENTITY_DOCUMENT_REQUIRED),
            new IdentityDocumentRule<>(IDENTITY_DOCUMENT_FIELD, LoanApplication::getIdentityDocument, ErrorCode.LOAN_IDENTITY_DOCUMENT_SIZE),
            new RequiredRule<>(REQUESTED_AMOUNT_FIELD, LoanApplication::getRequestedAmount, ErrorCode.LOAN_REQUESTED_AMOUNT_REQUIRED),
            new RangeRule<>(REQUESTED_AMOUNT_FIELD, LoanApplication::getRequestedAmount, REQUESTED_AMOUNT_MIN_STRING, REQUESTED_AMOUNT_MAX_STRING, ErrorCode.LOAN_REQUESTED_AMOUNT_OUT_OF_RANGE),
            new RequiredRule<>(TERM_MONTHS_FIELD, LoanApplication::getTermMonths, ErrorCode.LOAN_TERM_MONTHS_REQUIRED),
            new RangeRule<>(TERM_MONTHS_FIELD, app -> app.getTermMonths() == null ? null : java.math.BigDecimal.valueOf(app.getTermMonths()), TERM_MONTHS_MIN_STRING, TERM_MONTHS_MAX_STRING, ErrorCode.LOAN_TERM_MONTHS_OUT_OF_RANGE),
            new RequiredRule<>(LOAN_TYPE_ID_FIELD, LoanApplication::getLoanTypeId, ErrorCode.LOAN_TYPE_ID_REQUIRED),
            new LoanTypeExistsRule<>(LOAN_TYPE_ID_FIELD, validLoanTypeIds, LoanApplication::getLoanTypeId, ErrorCode.LOAN_TYPE_ID_REQUIRED),
            new RequiredRule<>(STATUS_FIELD, LoanApplication::getStatus, ErrorCode.LOAN_STATUS_REQUIRED),
            new RequiredRule<>(REGISTRATION_DATE_FIELD, LoanApplication::getRegistrationDate, ErrorCode.LOAN_REGISTRATION_DATE_REQUIRED)
        );
    }
}
