package de.weichert.multitenancy.exception;

/**
 * Created by Jannik on 21.03.15.
 */
public class TenantMigrationException extends RuntimeException {

    public TenantMigrationException() {
        super();
    }

    public TenantMigrationException(String message) {
        super(message);
    }

    public TenantMigrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantMigrationException(Throwable cause) {
        super(cause);
    }

    public TenantMigrationException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
