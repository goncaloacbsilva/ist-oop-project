package ggc.core.exception;

/**
 * Exception for unknown object keys
 */
public class UnknownObjectKeyException extends Exception {

    /** The requested objectId */
    private String _objectId;

    /** Class serial number. */
    private static final long serialVersionUID = 201409301048L;

    /**
     * @param objectId
     */
    public UnknownObjectKeyException(String objectId) {
        super("Objeto nao encontrado: " + objectId);
        _objectId = objectId;
    }

    /**
     * @return the requested object key
     */
    public String getObjectKey() {
        return _objectId;
    }
    
}
