package model.entity;

import java.time.LocalDateTime;
import model.enums.PaymentStatus;

public class Payment {
	private int paymentId;
	private int bookingId;
	private double amount;
	private PaymentStatus status;
	private LocalDateTime paymentDate;

	public Payment() {
	}

	public Payment(int paymentId, int bookingId, double amount, PaymentStatus status, LocalDateTime paymentDate) {
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.amount = amount;
		this.status = status;
		this.paymentDate = paymentDate;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
}
