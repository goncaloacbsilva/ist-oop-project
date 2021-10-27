package ggc.core.exception;

/**
 * Exception for unknown object keys
 */
public class UnknownObjectKeyException extends Exception {

    private String _objectId;

    /** Class serial number. */
    private static final long serialVersionUID = 201409301048L;

    public UnknownObjectKeyException(String objectId) {
        super("UnknownObjectKey: " + objectId);
        _objectId = objectId;
    }

    public String getObjectKey() {
        return _objectId;
    }
    
}
