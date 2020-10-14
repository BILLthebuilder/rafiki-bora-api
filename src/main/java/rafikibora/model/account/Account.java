package rafikibora.model.account;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafikibora.model.transactions.Transaction;
import rafikibora.model.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private int id;

    @Column(name = "name",nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "account_number")
    private String accountNumber = UUID.randomUUID().toString().replaceAll("[^0.05]","2");

    @Column(name = "pan",nullable = false, columnDefinition = "INT(19)")
    private int pan;

    @Column(name = "phone_number",nullable = false, columnDefinition = "VARCHAR(10)")
    private String phoneNumber;

    @Column(name = "is_deleted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(name = "date_created", updatable=false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "date_updated", columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @ManyToOne
    @JoinColumn(name="created_by", nullable = false, referencedColumnName = "userid", insertable = false, updatable = false)
    @JsonIgnore
    private User accountMaker;

    @Column(name = "created_by")
    private Integer accountMakers;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="approved_by", referencedColumnName = "userid")
    private User accountChecker;

    @JsonIgnore
    @OneToOne(mappedBy="userAccount",cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "status", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean status;

    @Column(name = "balance", columnDefinition = "DOUBLE(12,2) DEFAULT 0.00")
    private double balance;

    @OneToMany(mappedBy="sourceAccount",cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> withdrawals = new ArrayList<Transaction>();

    @OneToMany(mappedBy="destinationAccount",cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> deposits = new ArrayList<Transaction>();
}
