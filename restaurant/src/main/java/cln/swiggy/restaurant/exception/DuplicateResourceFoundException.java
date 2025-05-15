package cln.swiggy.restaurant.exception;

public class DuplicateResourceFoundException extends RuntimeException {
    public DuplicateResourceFoundException(String message) {
        super(message);
    }
}
