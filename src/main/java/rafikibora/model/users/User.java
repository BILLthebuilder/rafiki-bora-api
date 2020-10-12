package rafikibora.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafikibora.dto.UserSummary;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(15)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(15)")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean status;

    @Column(name = "mid", unique = true, columnDefinition = "VARCHAR(34)")
    private String mid;

    @Column(name = "business_name", columnDefinition = "VARCHAR(35)")
    private String businessName;

    @Column(name = "phone_no", nullable = false, columnDefinition = "VARCHAR(10)")
    private String phoneNo;

    @Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(name = "date_created", updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "date_updated", columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User userMaker;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "approved_by", referencedColumnName = "user_id")
    private User userChecker;

//     @NotNull
//     @Enumerated(EnumType.STRING)
//     private AuthProvider provider;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Roles> roles = new HashSet<>();

    public UserSummary toUserSummary() {
        UserSummary userSummary = new UserSummary();
        userSummary.setEmail(this.email);
        userSummary.setUserId(this.userId);
        return userSummary;
    }

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_id")
    private Account userAccount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "Agents_Terminals",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "terminal_id")}
    )
    List<Terminal> assignedTerminals = new ArrayList<Terminal>();

}



