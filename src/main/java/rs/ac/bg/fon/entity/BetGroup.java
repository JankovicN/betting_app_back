package rs.ac.bg.fon.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BET_GROUP")
public class BetGroup {
    @Id
    @Column(name="BET_GROUP_ID")
    private Integer id;
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "betGroup")
    private List<Odd> odds;

    public List<Odd> getOdds() {
        if (odds==null){
            odds=new ArrayList<>();
        }
        return odds;
    }

    public void setOdds(List<Odd> odds) {
        this.odds = odds;
    }
}
