package model.service;

import java.util.List;

import exception.InvalidInputException;

import model.dao.RoomDAO;
import model.entity.Room;
import model.enums.RoomType;

public class RoomService {

    private final RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
    }

    
    public void createRoom(RoomType roomType, double pricePerDay) {

        if (roomType == null) {
            throw new InvalidInputException("Room type cannot be null");
        }

        if (pricePerDay <= 0) {
            throw new InvalidInputException("Room price must be greater than zero");
        }

        Room room = new Room();
        room.setRoomType(roomType);
        room.setPricePerDay(pricePerDay);
        room.setAvailable(true);

        roomDAO.createRoom(room);
    }

    
    public Room getRoomById(int roomId) {
        if (roomId <= 0) {
            throw new InvalidInputException("Invalid room ID");
        }
        return roomDAO.getRoomById(roomId);
    }

    
    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    
    public List<Room> getAvailableRooms() {
        return roomDAO.getAvailableRooms();
    }

    
    public List<Room> getAvailableRoomsByType(RoomType roomType) {
        if (roomType == null) {
            throw new InvalidInputException("Room type cannot be null");
        }
        return roomDAO.getAvailableRoomsByType(roomType);
    }

   
    public void updateRoomAvailability(int roomId, boolean available) {
        if (roomId <= 0) {
            throw new InvalidInputException("Invalid room ID");
        }
        roomDAO.updateRoomAvailability(roomId, available);
    }

    
    public void deleteRoom(int roomId) {
        if (roomId <= 0) {
            throw new InvalidInputException("Invalid room ID");
        }
        roomDAO.deleteRoom(roomId);
    }
}
