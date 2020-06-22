package com.mtucholski.reservation.app.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClinicException extends RuntimeException {

    public enum ExceptionType{

        BASIC ("Clinic App error. Please contact with admin."),
        CLINIC_MODULE_LOST("Clinic module is lost. Our maintenance team is working and solving problem"),
        BAD_REQUEST("Bad request. Please try again in the minute"),
        BAD_SERVICE_CALLING("Failure when trying to start service.. Please try again latter or call admin"),
        UNEXPECTED_RESULT("Unexpected service result. Please contact with admin"),
        BAD_ENTRY_DATA("Wrong entry data"),
        BAD_LOGIN_USER_OR_PASSWORD("Wrong authentication data."),
        PLAIN_TEXT("");
        private String statementInfo;
        ExceptionType(String statementInfo){

            this.statementInfo = statementInfo;
        }

        public String getStatementInfo(){

            return statementInfo;
        }
    }

    private ExceptionType exceptionType = ExceptionType.BASIC;
    private String logMessage;

    public ClinicException (String message){

        super(message);
    }

    public ClinicException(Throwable cause){

        super(cause);
    }

    public ClinicException(String message, Throwable cause){

        super(message, cause);
    }

    public ClinicException(ExceptionType exceptionType){

        super();
        this.exceptionType = exceptionType;
    }

    public ClinicException(ExceptionType exceptionType, String message){

        super(message);
        this.exceptionType = exceptionType;
    }

    public ClinicException(ExceptionType exceptionType, Throwable cause){

        super(cause);
        this.exceptionType = exceptionType;
    }

    public ClinicException(ExceptionType exceptionType, String message, Throwable cause){

        super(message, cause);
        this.exceptionType = exceptionType;
    }

    public ClinicException(ExceptionType exceptionType, String message, Throwable cause, String logMessage){

        super(message, cause);
        this.exceptionType = exceptionType;
        this.logMessage = logMessage;
    }

    public ClinicException(ExceptionType exceptionType, String message, Throwable cause, String logMessage, boolean enableSuppression,
                           boolean writableStackTrace){

        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionType = exceptionType;
        this.logMessage = logMessage;
    }

    public ExceptionType getExceptionType(){

        return exceptionType;
    }

    @Override
    public String getMessage() {

        StringBuilder builder = new StringBuilder();

        if (exceptionType.equals(ExceptionType.PLAIN_TEXT)){

            builder.append(super.getMessage());
        }else{

            builder.append(exceptionType.getStatementInfo());

            if (super.getMessage() !=null){

                builder.append("").append(super.getMessage());
            }
        }

        return builder.toString();
    }
}
