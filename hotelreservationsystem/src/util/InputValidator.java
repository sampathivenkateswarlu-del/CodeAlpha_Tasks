package util;

import exception.InvalidInputException;

public final class InputValidator {
	private InputValidator() {
	}

	public static void validatePositiveNumber(int value, String fieldName) {
		if (value <= 0) {
			throw new InvalidInputException(fieldName + " must be greater than zero");
		}
	}

	public static void validateNotNull(Object value, String fieldName) {
		if (value == null) {
			throw new InvalidInputException(fieldName + " cannot be null");
		}
	}

	public static void validateNotEmpty(String value, String fieldName) {
		if (value == null || value.trim().isEmpty()) {
			throw new InvalidInputException(fieldName + " cannot be empty");
		}
	}
}
