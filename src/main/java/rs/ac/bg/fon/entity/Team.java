package rs.ac.bg.fon.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {

    @Id
    private Integer id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="home", fetch = FetchType.EAGER)
    private List<Fixture> home;
    @JsonIgnore
    @OneToMany(mappedBy="away", fetch = FetchType.EAGER)
    private List<Fixture> away;

    public List<Fixture> getHome() {
        if(this.home==null){
            this.home=new ArrayList<>();
        }
        return home;
    }

    public List<Fixture> getAway() {
        if(this.away==null){
            this.away=new ArrayList<>();
        }
        return away;
    }

    public void setHome(List<Fixture> home) {
        if(this.home==null){
            this.home=new ArrayList<>();
        }
        this.home = home;
    }

    public void setAway(List<Fixture> away) {
        if(this.away==null){
            this.away=new ArrayList<>();
        }
        this.away = away;
    }
}
