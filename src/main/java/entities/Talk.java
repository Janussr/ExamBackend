package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Talk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String topic;
    private int duration;
    private String propsList;

    @ManyToMany
    private List<Speaker> speakers;

    @ManyToOne
    private Conference conference;

    public Talk() {
    }

    public Talk(String topic, int duration, String propsList) {
        this.topic = topic;
        this.duration = duration;
        this.propsList = propsList;
        this.speakers = new ArrayList<>();
    }

    public Talk(String topic, int duration, String propsList, Conference conference) {
        this.topic = topic;
        this.duration = duration;
        this.propsList = propsList;
        this.speakers = new ArrayList<>();
        this.conference = conference;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPropsList() {
        return propsList;
    }

    public void setPropsList(String propsList) {
        this.propsList = propsList;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
