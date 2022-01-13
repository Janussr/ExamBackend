package dtos.Talk;

import entities.Talk;

import java.util.List;
import java.util.Objects;

public class TalkDTOs {
    private List<TalkDTO> talks;

    public int getSize() {
        int counter = 0;
        for (TalkDTO t : talks) {
            counter++;
        }
        return counter;
    }

    //making a list of the dto class.
    public TalkDTOs(List<Talk> talks) {
        this.talks = TalkDTO.getFromList(talks);
    }

    public List<TalkDTO> getTalks() {
        return talks;
    }

    public void setTalks(List<TalkDTO> talks) {
        this.talks = talks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalkDTOs talkDTOs = (TalkDTOs) o;
        return Objects.equals(talks, talkDTOs.talks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talks);
    }

    @Override
    public String toString() {
        return "TalkDTOs{" +
                "talks=" + talks +
                '}';
    }
}
