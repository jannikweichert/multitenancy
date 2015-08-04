package de.weichert.multitenancy.exception;

/**
 * Created by Jannik on 21.03.15.
 */
public class TenantScopeNotAvailableException extends RuntimeException {

    public TenantScopeNotAvailableException() {
        super();
    }

    public TenantScopeNotAvailableException(String message) {
        super(message);
    }

    public TenantScopeNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantScopeNotAvailableException(Throwable cause) {
        super(cause);
    }

    public TenantScopeNotAvailableException(String message,
                                            Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
