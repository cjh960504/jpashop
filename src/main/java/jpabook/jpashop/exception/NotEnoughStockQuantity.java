package jpabook.jpashop.exception;

public class NotEnoughStockQuantity extends RuntimeException {
    public NotEnoughStockQuantity() {
    }

    public NotEnoughStockQuantity(String message) {
        super(message);
    }

    public NotEnoughStockQuantity(Throwable cause) {
        super(cause);
    }

    public NotEnoughStockQuantity(String message, Throwable cause) {
        super(message, cause);
    }
}
