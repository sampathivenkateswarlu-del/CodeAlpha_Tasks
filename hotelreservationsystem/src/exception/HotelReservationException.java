package exception;

public class HotelReservationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public HotelReservationException(String message) {
		super(message);
	}

	public HotelReservationException(String message, Throwable cause) {
		super(message, cause);
	}
}
