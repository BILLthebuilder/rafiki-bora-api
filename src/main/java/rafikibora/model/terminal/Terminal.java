package rafikibora.model.terminal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.Nullable;
import rafikibora.model.transactions.Transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "terminals")
public class Terminal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="terminal_id", columnDefinition = "INT(10)")
    private Long id;

    @Column(name = "tid",nullable = false, unique = true, columnDefinition = "VARCHAR(16)")
    private String tid;

    @Column(name = "serial_no",nullable = false, unique = true, columnDefinition = "VARCHAR(28)")
    private String serialNo;

    @Column(name = "model_type",nullable = false, columnDefinition = "VARCHAR(10)")
    private String modelType;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean status;

//    @JsonBackReference(value = "mid_t")
//    @ManyToOne
//    @JoinColumn(name="mid", referencedColumnName = "mid")
//    private User merchant;
//
//    @JsonBackReference(value = "created_by_t")
//    @ManyToOne
//    @JoinColumn(name="created_by", nullable = false, referencedColumnName = "user_id")
//    private User terminalMaker;
//
//    @JsonBackReference(value = "approved_by_t")
//    @ManyToOne
//    @Nullable
//    @JoinColumn(name="approved_by", referencedColumnName = "user_id")
//    private User terminalChecker;


    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isDeleted;

    @Column(name = "date_added", updatable=false, nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "date_updated", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @OneToMany(mappedBy="terminal",cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "tid_t")
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    public User getMerchant() {
//        return merchant;
//    }
//
//    public void setMerchant(User merchant) {
//        this.merchant = merchant;
//    }
//
//    public User getTerminalMaker() {
//        return terminalMaker;
//    }
//
//    public void setTerminalMaker(User terminalMaker) {
//        this.terminalMaker = terminalMaker;
//    }
//
//    public User getTerminalChecker() {
//        return terminalChecker;
//    }
//
//    public void setTerminalChecker(User terminalChecker) {
//        this.terminalChecker = terminalChecker;
//    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}