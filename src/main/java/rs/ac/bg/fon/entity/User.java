package rs.ac.bg.fon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name="USER_ID")
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Date birthday=null;
    private String username;
    private String password;


    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();


}
