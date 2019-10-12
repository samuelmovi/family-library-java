package samuelmovi.familyLibraryJava.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long index;
    private String address;
    private  String room;
    private  String furniture;
    private  String details;
    private  String registrationDate;
    private  String modificationDate;

    protected Location(){}

    public Location(String address, String room, String furniture, String details){
        this.address = address;
        this.room = room;
        this.furniture = furniture;
        this.details = details;
        this.registrationDate = String.valueOf(LocalDate.now());
    }

    @Override
    public String toString() {
        return String.format(
                "Location[id=%d, address='%s', room='%s', furniture=%s, details=%s]",
                index, address, room, furniture, details);
    }

    public long getIndex() {
        return index;
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

    @Column(name="registration_date")
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Column(name="modification_date")
    public String getModificationDate() {
        return modificationDate;
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

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
