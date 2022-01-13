package dtos.Talk;

import dtos.Conference.ConferenceDTO;
import dtos.Speaker.SpeakerDTO;
import entities.Talk;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TalkDTO {
    private Integer id;
    private String topic;
    private int duration;
    private String propsList;
    private List<SpeakerDTO> speakers;
    private ConferenceDTO conference;


    public TalkDTO(Talk talk) {
        this.id = talk.getId();
        this.topic = talk.getTopic();
        this.duration = talk.getDuration();
        this.propsList = talk.getPropsList();
        //When we create a talk we give it a speaker.
        this.speakers = SpeakerDTO.getFromList(talk.getSpeakers());
        //When we create a talk, it has to have a conference.
        this.conference = new ConferenceDTO(talk.getConference());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ConferenceDTO getConference() {
        return conference;
    }

    public void setConference(ConferenceDTO conference) {
        this.conference = conference;
    }

    public List<SpeakerDTO> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerDTO> speakers) {
        this.speakers = speakers;
    }

    //Used to make a list of dto, so we can use it in DTO's class.
    public static List<TalkDTO> getFromList(List<Talk> talks) {
        return talks.stream()
                .map(talk -> new TalkDTO(talk))
                .collect(Collectors.toList());
    }

    //In case I need it for testing


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalkDTO talkDTO = (TalkDTO) o;
        return duration == talkDTO.duration && Objects.equals(id, talkDTO.id) && Objects.equals(topic, talkDTO.topic) && Objects.equals(propsList, talkDTO.propsList) && Objects.equals(speakers, talkDTO.speakers) && Objects.equals(conference, talkDTO.conference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, duration, propsList, speakers, conference);
    }

    @Override
    public String toString() {
        return "TalkDTO{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", duration=" + duration +
                ", propsList='" + propsList + '\'' +
                ", speakers=" + speakers +
                ", conference=" + conference +
                '}';
    }
}
