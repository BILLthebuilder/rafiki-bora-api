package rafikibora.model.terminal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;
import rafikibora.model.transactions.Transaction;
import rafikibora.model.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "terminals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Terminal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="terminal_id", columnDefinition = "INT(10)")
    private Long id;

    @Column(name = "tid", unique = true, columnDefinition = "VARCHAR(16)")
    private String tid;

    @Column(name = "serial_no", unique = true, columnDefinition = "VARCHAR(28)")
    private String serialNo;

    @Column(name = "model_type", columnDefinition = "VARCHAR(10)")
    private String modelType;

    @Column(name = "status",  nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean status;


    @ManyToOne
    @JoinColumn(name="mid", referencedColumnName = "mid")
    @JsonIgnore
    private User merchant;


    @ManyToOne
    @JoinColumn(name="created_by", nullable = false, referencedColumnName = "user_id")
    @JsonIgnore
    private User terminalMaker;

    @ManyToOne
    @JoinColumn(name="approved_by", referencedColumnName = "user_id")
    @JsonIgnore
    private User terminalChecker;


    @Column(name = "is_deleted",  nullable = false,  columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @CreationTimestamp
    @Column(name = "date_added", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "date_updated", columnDefinition = "DATETIME ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToMany(mappedBy="terminal",cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<Transaction>();

}