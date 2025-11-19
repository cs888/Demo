package lld;

import java.util.Date;
import java.util.List;

public class Hotel {
    int Id;
    String name;
    Location location;
    List<Room> roomList;
}

class Location {
    int pin;
    String area;
    String street;
    String city;
    String country;
}
class Room {
    String roomNumber;
    RoomStyle roomType;
    RoomStatus roomStatus;
    int price;
    List<RoomKey> roomKeys;
    List<HouseKeepingLog> houseKeepingLogs;
}
 enum RoomStyle {
    DELUXE,VIP,NORMAL,STANDAR,FAMILY_SUITE
}

 enum RoomStatus {
    RESERVED,AVAIABLE,NA , SERVICE_IN_PROGRESS
}

class RoomKey {
    String keyId;
    String barcode;
    Date issuedAt;
    boolean IsActive;
    boolean isMaster;

    public void assignRoom(Room room){}
}

 class HouseKeepingLog {
    String activityDescription;
    int interval;
    Date date;
    HouseKeeper houseKeeper;

     public void aadRoomLog(Room room){}

 }

 class Person {
     String name;
     Account accountDetail;
     String phone;

 }

class Account {
    String email;
    String password;
    AccountStatus accountStatus;
}
 enum AccountStatus {
    ACTIVE,CLOSED,BLOCKED
}
 class HouseKeeper extends Person{

     public List<Room> getRoomServiced(Date date) {return null;}
}

class Guest extends Person{
    Search searchObj;
    Booking bookingObj;

    public List<Room> getAllBooking(Date date) {return null;}

}

class Search {

     public List<Room> searchRoom(RoomStyle roomStyle, Date date){return  null;}
}

class Booking {
    public List<Room> createBooking(Guest guestInfo){return  null;}
    public void cancelBooking(Guest guestInfo){}

}

class Receptionist extends Person {
    Search search;
    Booking booking;
    public void checkin(Guest guest, BookingInfo bookingInfo){  }
    public void checkOut(Guest guest, BookingInfo bookingInfo){  }
}

class Admin extends Person {
    public void addRoom(Room room){};
    public void deleteRoom(Room room){};
    public void editRoom(Room room){};
}

class BookingInfo {
    String id;
    Date startDate;
    int duration;
    BookingStatus status;
    List<Guest> guests;
    List<Room> roomList;
    BaseRoomCharge baseRoomCharge;

}
interface BaseRoomCharge {
    Double getCost();

    void setCost(double v);
}

class RoomCharge implements BaseRoomCharge {
    double cost;
    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost){
        this.cost=cost;
    }
}

class RoomServiceCharge implements BaseRoomCharge {
    double cost;
    BaseRoomCharge baseRoomCharge;
    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost){
        baseRoomCharge.setCost(baseRoomCharge.getCost()+cost);
        baseRoomCharge.getCost();
    }
}

class InRoomPurchaeCharge implements BaseRoomCharge {
    double cost;
    BaseRoomCharge baseRoomCharge;
    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost){
        baseRoomCharge.setCost(baseRoomCharge.getCost()+cost);
        baseRoomCharge.getCost();
    }
}
enum BookingStatus {
BOOKED,CANCELLED,REFUNDED
}