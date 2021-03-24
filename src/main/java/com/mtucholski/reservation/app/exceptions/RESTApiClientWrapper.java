package com.mtucholski.reservation.app.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Level;

@Log
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class RESTApiClientWrapper<T> {

    private Supplier<T> request;
    private boolean requiredNotNull = true;
    private String messageDataIsNull = "No data";
    private String messageDataNotFound = "Data not found";
    private String errorMessage = "error";

    public RESTApiClientWrapper(Supplier<T> request) {

        this.request = request;
    }

    public static <U> RESTApiClientWrapper<U> request(Supplier<U> request) {

        return new RESTApiClientWrapper<>(request);
    }

    /**
     * Set method when data is null
     *
     * @param message information to logs and screen when data is null
     * @return this which is equal to messageDataIsNull
     */
    public RESTApiClientWrapper<T> messageDataIsNull(String message) {

        this.messageDataIsNull = message;
        return this;
    }

    /**
     * Set method when data isn't found
     *
     * @param message information to logs and screen when data is not found
     * @return this which is equal to messageDataNotFound
     */
    public RESTApiClientWrapper<T> messageDataNotFound(String message) {

        this.messageDataNotFound = message;
        return this;
    }

    /**
     * @param errorMessage information to logs and screen when error happen
     * @return this which is equal to error message
     */
    public RESTApiClientWrapper<T> messageError(String errorMessage) {

        this.errorMessage = errorMessage;
        return this;
    }

    /**
     * Private method for logging errors
     * @param error exception
     */
    private void logError(Exception error) {

        if (this.errorMessage != null && this.errorMessage.trim().isEmpty()) {

            log.log(Level.SEVERE, this.errorMessage + "" + error.getMessage());
        }
    }

    public T execute() {

        try {

            return requiredNotNull ? Objects.requireNonNull(request.get()) : request.get();

        } catch (NullPointerException exception) {

            log.log(Level.SEVERE, this.errorMessage + "" + exception.getMessage());
            throw new ClinicException(ClinicException.ExceptionType.DATA_IS_NULL, messageDataIsNull, exception.getCause(),
                    errorMessage);

        } catch (HttpClientErrorException errorException) {

            log.log(Level.SEVERE, this.errorMessage + "" + errorException.getMessage());

            if (errorException.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

                logError(errorException);
                log.log(Level.SEVERE, this.errorMessage + "" + errorException.getMessage());
                throw new ClinicException(ClinicException.ExceptionType.BAD_ENTRY_DATA, errorException.getMessage(), errorException, errorMessage);
            }

            throw new ClinicException(ClinicException.ExceptionType.BAD_REQUEST, errorException.getMessage(), errorException, errorMessage);

        } catch (RestClientException error) {

            logError(error);
            log.log(Level.SEVERE, this.errorMessage + "" + error.getMessage());
            throw new ClinicException(ClinicException.ExceptionType.BAD_SERVICE_CALLING, error.getMessage(), error,
                    errorMessage);
        }
    }
}

