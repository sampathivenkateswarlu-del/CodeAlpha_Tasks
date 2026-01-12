package exception;

public class RoomNotAvailableException extends HotelReservationException {
	private static final long serialVersionUID = 1L;

	public RoomNotAvailableException(String message) {
		super(message);
	}
}