package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class DateUtil {
	private DateUtil() {
	}

	public static LocalDate today() {
		return LocalDate.now();
	}

	public static long daysBetween(LocalDate start, LocalDate end) {
		return ChronoUnit.DAYS.between(start, end);
	}

	public static boolean isPastDate(LocalDate date) {
		return date.isBefore(LocalDate.now());
	}
}
