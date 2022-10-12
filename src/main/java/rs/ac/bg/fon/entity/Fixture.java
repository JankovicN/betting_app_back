package rs.ac.bg.fon.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FIXTURE")
public class Fixture {

    @Id
    @Column(name="FIXTURE_ID")
    private Integer id;
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="home_id")
    private Team home;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="away_id")
    private Team away;

    private int homeGoals;
    private int awayGoals;
    private String state="NS";

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="LEAGUE_ID", nullable=false)
    private League league;

    @JsonIgnore
    @OneToMany(mappedBy = "fixture")
    private List<Odd> odds;


}
