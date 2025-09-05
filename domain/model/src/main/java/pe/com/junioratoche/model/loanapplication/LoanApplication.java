package pe.com.junioratoche.model.loanapplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;
import pe.com.junioratoche.model.loanapplication.validation.annotations.*;

import static pe.com.junioratoche.model.error.ErrorCode.*;
import static pe.com.junioratoche.model.loanapplication.validation.provider.LoanApplicationValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class LoanApplication {
    private UUID id;

    @Required(code = LOAN_CLIENT_ID_REQUIRED, field = CLIENT_ID_FIELD)
    private String clientId;

    @Required(code = LOAN_IDENTITY_DOCUMENT_REQUIRED, field = IDENTITY_DOCUMENT_FIELD)
    @Size(min = IDENTITY_DOCUMENT_MIN_LENGTH, max = IDENTITY_DOCUMENT_MAX_LENGTH, code = LOAN_IDENTITY_DOCUMENT_SIZE, field = IDENTITY_DOCUMENT_FIELD)
    private String identityDocument;

    @Required(code = LOAN_REQUESTED_AMOUNT_REQUIRED, field = REQUESTED_AMOUNT_FIELD)
    @Range(min = REQUESTED_AMOUNT_MIN_STRING, max = REQUESTED_AMOUNT_MAX_STRING, code = LOAN_REQUESTED_AMOUNT_OUT_OF_RANGE, field = REQUESTED_AMOUNT_FIELD)
    private BigDecimal requestedAmount;

    @Required(code = LOAN_TERM_MONTHS_REQUIRED, field = TERM_MONTHS_FIELD)
    @Range(min = TERM_MONTHS_MIN_STRING, max = TERM_MONTHS_MAX_STRING, code = LOAN_TERM_MONTHS_OUT_OF_RANGE, field = TERM_MONTHS_FIELD)
    private Integer termMonths;

    @Required(code = LOAN_TYPE_ID_REQUIRED, field = LOAN_TYPE_ID_FIELD)
    private String loanTypeId;

    @Required(code = LOAN_STATUS_REQUIRED, field = STATUS_FIELD)
    private String status;

    @Required(code = LOAN_REGISTRATION_DATE_REQUIRED, field = REGISTRATION_DATE_FIELD)
    private LocalDateTime registrationDate;
}
