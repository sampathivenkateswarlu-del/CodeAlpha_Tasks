package model.entity;

import model.enums.RoomType;

public class Room {
	private int roomId;
	private RoomType roomType;
	private boolean available;
	private double pricePerDay;

	public Room() {
	}

	public Room(int roomId, RoomType roomType, boolean available, double pricePerDay) {
		this.roomId = roomId;
		this.roomType = roomType;
		this.available = available;
		this.pricePerDay = pricePerDay;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
}
