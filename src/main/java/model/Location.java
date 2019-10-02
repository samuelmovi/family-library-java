package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long location_index;
    String address;
    String room;
    String furniture;
    String details;
    String registration_date;
    String modification_date;

    protected Location(){}

    public Location(String address, String room, String furniture, String details){
        this.address = address;
        this.room = room;
        this.furniture = furniture;
        this.details = details;
        this.registration_date = String.valueOf(LocalDate.now());
        // this.modification_date = modification_date;
    }

    @Override
    public String toString() {
        return String.format(
                "Location[id=%d, address='%s', room='%s', furniture=%s]",
                location_index, address, room, furniture);
    }

    public long getLocation_index() {
        return location_index;
    }

    public String getAddress() {
        return address;
    }

    public String getRoom() {
        return room;
    }

    public String getFurniture() {
        return furniture;
    }

    public String getDetails() {
        return details;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }
}
