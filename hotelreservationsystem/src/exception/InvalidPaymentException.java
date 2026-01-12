package exception;

public class InvalidPaymentException extends HotelReservationException {
	private static final long serialVersionUID = 1L;

	public InvalidPaymentException(String message) {
		super(message);
	}
}