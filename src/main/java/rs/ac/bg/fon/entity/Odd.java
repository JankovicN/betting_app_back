package rs.ac.bg.fon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ODDS")
public class Odd {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ODD_ID")
    private Integer id;
    private BigDecimal odd;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FIXTURE_ID")
    private Fixture fixture;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BET_GROUP_ID")
    private BetGroup betGroup;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "odd")
    private Bet bet;

}
