package rafikibora.model.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;
import rafikibora.model.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "primary_account_number", nullable=false, updatable=false, columnDefinition = "INT(16)")
    private int pan;

    @Column(name = "processing_code", nullable=false, updatable=false, columnDefinition = "INT(6)")
    private int processingCode;

    @Column(name = "amount_transaction", nullable=false, updatable=false, columnDefinition = "DOUBLE(12,2)")
    private double amountTransaction;

    @Column(name = "date_time_transmission", updatable=false, nullable=false, columnDefinition = "DATETIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateTimeTransmission;

    @Column(name = "stan", columnDefinition = "INT(6)")
    private int stan;

    @Column(name = "date_time_local_transaction", updatable=false, nullable=false, columnDefinition = "DATETIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateTimeLocalTransaction;

    @Column(name = "pos_entry_mode", columnDefinition = "INT(3)")
    private int posEntryMode;

    @Column(name = "function_code", columnDefinition = "INT(3)")
    private int functionCode;

    @Column(name = "pos_condition_code", columnDefinition = "INT(2)")
    private int posConditionCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="tid", nullable = false, referencedColumnName = "tid")
    private Terminal terminal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="mid", nullable = false, referencedColumnName = "mid")
    private User merchant;

    @Column(name = "recipient_email", columnDefinition = "VARCHAR(30)")
    private String recipientEmail;

    @Column(name = "token", columnDefinition = "VARCHAR(9)")
    private String token;

    @Column(name = "currency_code", nullable=false, columnDefinition = "VARCHAR(3)")
    private String amountTransactionCurrencyCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="debit_account", referencedColumnName = "account_number", columnDefinition = "VARCHAR(10)")
    private Account sourceAccount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="credit_account", referencedColumnName = "account_number", columnDefinition = "VARCHAR(10)")
    private Account destinationAccount;

    @Column(name = "result_code",columnDefinition = "VARCHAR(6)")
    private String resultCode;

    @Transient
    private String sourceAccountNumber;

    @Transient
    private String destAccountNumber;
}
