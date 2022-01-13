package dtos.Conference;

import entities.Conference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConferenceDTO {
    private Integer id;
    private String name;
    private String location;
    private int capacity;
    private String date;
    private String time;
    //private List<talkDTO> talks;


    public ConferenceDTO(Conference conference) {
        this.id = conference.getId();
        this.name = conference.getName();
        this.location = conference.getLocation();
        this.capacity = conference.getCapacity();
        this.date = conference.getDate();
        this.time = conference.getTime();
    }

    public static List<ConferenceDTO> getFromList(List<Conference> conferences) {
        return conferences.stream()
                .map(conference -> new ConferenceDTO(conference))
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    //BELOW CODE IS USED FOR TESTING


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceDTO that = (ConferenceDTO) o;
        return capacity == that.capacity && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(location, that.location) && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location, capacity, date, time);
    }

    @Override
    public String toString() {
        return "ConferenceDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
