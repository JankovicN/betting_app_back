package rs.ac.bg.fon.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="TICKET_ID")
    private Integer id;
    private BigDecimal wager;
    private double odd;
    private BigDecimal totalWin;
    private LocalDateTime date;
    private String state;

    @JsonIgnore
    @OneToMany(mappedBy="ticket")
    private List<Bet> bets;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    public List<Bet> getBets() {
        if(this.bets==null){
            bets=new ArrayList<>();
        }
        return bets;
    }
}
