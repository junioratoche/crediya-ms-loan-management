package pe.com.junioratoche.model.loanapplication.validation.provider;

import java.math.BigDecimal;

public final class LoanApplicationValidationConstants {

    public static final String CLIENT_ID_FIELD = "clientId";
    public static final String IDENTITY_DOCUMENT_FIELD = "identityDocument";
    public static final String REQUESTED_AMOUNT_FIELD = "requestedAmount";
    public static final String TERM_MONTHS_FIELD = "termMonths";
    public static final String LOAN_TYPE_ID_FIELD = "loanTypeId";
    public static final String STATUS_FIELD = "status";
    public static final String REGISTRATION_DATE_FIELD = "registrationDate";

    public static final int IDENTITY_DOCUMENT_MIN_LENGTH = 8;
    public static final int IDENTITY_DOCUMENT_MAX_LENGTH = 20;
    public static final String REQUESTED_AMOUNT_MIN_STRING = "1";
    public static final String REQUESTED_AMOUNT_MAX_STRING = "1000000";
    public static final String TERM_MONTHS_MIN_STRING = "1";
    public static final String TERM_MONTHS_MAX_STRING = "120";

    private LoanApplicationValidationConstants() {}
}
