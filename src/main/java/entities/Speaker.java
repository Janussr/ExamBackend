package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String name;
    private String profession;
    private String gender;


    @ManyToMany(mappedBy = "speakers", cascade = CascadeType.PERSIST)
    List<Talk> talks;


    public Speaker(String name, String profession, String gender) {
        this.name = name;
        this.profession = profession;
        this.gender = gender;
        this.talks = new ArrayList<>();
    }

    public Speaker() {
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

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    //Adds a talk to a speaker (populates the link table between talk and speaker in SQL)
    public void addTalk(Talk talk){
        if (talk != null){
            this.talks.add(talk);
            talk.getSpeakers().add(this);
        }
    }
}
