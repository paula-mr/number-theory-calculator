package luiz787.uniquedigit.exception;

public class CryptoException extends RuntimeException {
    public CryptoException(Throwable t) {
        super(t);
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
