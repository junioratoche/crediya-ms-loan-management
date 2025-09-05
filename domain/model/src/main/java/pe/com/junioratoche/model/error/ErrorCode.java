package pe.com.junioratoche.model.error;

public enum ErrorCode {
    LOAN_CLIENT_ID_REQUIRED("LOAN_001", 400, "clientId is required"),
    LOAN_IDENTITY_DOCUMENT_REQUIRED("LOAN_002", 400, "identityDocument is required"),
    LOAN_IDENTITY_DOCUMENT_SIZE("LOAN_003", 400, "identityDocument size out of range"),
    LOAN_REQUESTED_AMOUNT_REQUIRED("LOAN_004", 400, "requestedAmount is required"),
    LOAN_REQUESTED_AMOUNT_OUT_OF_RANGE("LOAN_005", 400, "requestedAmount out of range"),
    LOAN_TERM_MONTHS_REQUIRED("LOAN_006", 400, "termMonths is required"),
    LOAN_TERM_MONTHS_OUT_OF_RANGE("LOAN_007", 400, "termMonths out of range"),
    LOAN_TYPE_ID_REQUIRED("LOAN_008", 400, "loanTypeId is required"),
    LOAN_STATUS_REQUIRED("LOAN_009", 400, "status is required"),
    LOAN_REGISTRATION_DATE_REQUIRED("LOAN_010", 400, "registrationDate is required");

    private final String code;
    private final int statusCode;
    private final String message;

    ErrorCode(String code, int statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String code()    { return code; }
    public int    statusCode()    { return statusCode; }
    public String message() { return message; }
}
