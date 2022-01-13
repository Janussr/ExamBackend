package dtos.Speaker;

import dtos.Conference.ConferenceDTO;
import entities.Conference;
import entities.Speaker;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpeakerDTO {
    private Integer id;
    private String name;
    private String profession;
    private String gender;


    public SpeakerDTO(Speaker speaker) {
        this.id = speaker.getId();
        this.name = speaker.getName();
        this.profession = speaker.getProfession();
        this.gender = speaker.getGender();
    }

    public static List<SpeakerDTO> getFromList(List<Speaker> speakers) {
        return speakers.stream()
                .map(speaker -> new SpeakerDTO(speaker))
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerDTO that = (SpeakerDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(profession, that.profession) && Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, profession, gender);
    }


    @Override
    public String toString() {
        return "SpeakerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
