package util;

import model.enums.RoomType;

public final class PriceCalculator {
	private PriceCalculator() {
	}

	public static double calculate(RoomType roomType, int days) {
		double pricePerDay;
		switch (roomType) {
		case STANDARD:
			pricePerDay = 1500;
			break;
		case DELUXE:
			pricePerDay = 2500;
			break;
		case SUITE:
			pricePerDay = 4000;
			break;
		default:
			throw new IllegalArgumentException("Invalid room type");
		}
		return pricePerDay * days;
	}
}
