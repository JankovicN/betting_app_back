package rs.ac.bg.fon.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="LEAGUE")
public class League {

    @Id
    @Column(name="LEAGUE_ID")
    private Integer id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy="league")
    private List<Fixture> fixtures;

}
