package dtos.Conference;

import entities.Conference;

import java.util.List;
import java.util.Objects;

public class ConferenceDTOs {
    private List<ConferenceDTO> conferenceDTOs;


    public ConferenceDTOs(List<Conference> conferenceDTOs) {
        this.conferenceDTOs = ConferenceDTO.getFromList(conferenceDTOs);
    }

    public List<ConferenceDTO> getConferenceDTOs() {
        return conferenceDTOs;
    }

    public void setConferenceDTOs(List<ConferenceDTO> conferenceDTOs) {
        this.conferenceDTOs = conferenceDTOs;
    }


    //In case I need it for testing.


    //Used to UnitTest "getAllTests"
    public int getSize() {
        int counter = 0;
        for (ConferenceDTO b : conferenceDTOs) {
            counter++;
        }
        return counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceDTOs that = (ConferenceDTOs) o;
        return Objects.equals(conferenceDTOs, that.conferenceDTOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conferenceDTOs);
    }

    @Override
    public String toString() {
        return "ConferenceDTOs{" +
                "conferenceDTOs=" + conferenceDTOs +
                '}';
    }
}
