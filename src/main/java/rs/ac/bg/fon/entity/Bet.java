package rs.ac.bg.fon.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BET")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BET_ID")
    private Integer id;
    private String state="NS";

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ODDS_ID", nullable = false)
    private Odd odd;

    @ManyToOne
    @JoinColumn(name="TICKET_ID", nullable=false)
    private Ticket ticket;

}
