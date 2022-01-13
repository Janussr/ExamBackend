package dtos.Speaker;

import entities.Speaker;

import java.util.List;
import java.util.Objects;

public class SpeakerDTOs {
    private List<SpeakerDTO> speakers;

    public int getSize() {
        int counter = 0;
        for (SpeakerDTO s : speakers) {
            counter++;
        }
        return counter;
    }

    public SpeakerDTOs(List<Speaker> speakers) {
        this.speakers = SpeakerDTO.getFromList(speakers);
    }

    public List<SpeakerDTO> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerDTO> speakers) {
        this.speakers = speakers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakerDTOs that = (SpeakerDTOs) o;
        return Objects.equals(speakers, that.speakers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speakers);
    }

    @Override
    public String toString() {
        return "SpeakerDTOs{" +
                "speakers=" + speakers +
                '}';
    }
}
