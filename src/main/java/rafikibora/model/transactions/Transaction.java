package rafikibora.model.transactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "pan",nullable = false, columnDefinition = "VARCHAR(16)")
    private String pan;

    @Column(name = "processing_code", nullable=false, updatable=false, columnDefinition = "VARCHAR(6)")
    private String processingCode;

    @Column(name = "amount_transaction", nullable=false, updatable=false, columnDefinition = "DOUBLE(12,2)")
    private double amountTransaction;

    @Column(name = "date_time_transmission", updatable=false, nullable=false, columnDefinition = "DATETIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateTimeTransmission;

    @Column(name = "stan", columnDefinition = "VARCHAR(6)")
    private String stan;

    @JsonFormat(pattern="hhmmss:MMDD")
    @Column(name = "date_time_local_transaction", updatable=false, nullable=false, columnDefinition = "DATETIME")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateTimeLocalTransaction;

    @Column(name = "pos_entry_mode", columnDefinition = "INT(3)")
    private int posEntryMode;

    @Column(name = "function_code", columnDefinition = "INT(3)")
    private int functionCode;

    @Column(name = "pos_condition_code", columnDefinition = "INT(2)")
    private String posConditionCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="terminal", nullable = false, referencedColumnName = "terminal_id")
    private Terminal terminal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="merchant", nullable = false, referencedColumnName = "userid")
    private User merchant;

    @Column(name = "recipient_email", columnDefinition = "VARCHAR(30)")
    private String recipientEmail;

    @Column(name = "token", columnDefinition = "VARCHAR(9)")
    private String token;

    @Column(name = "currency_code", nullable=false, columnDefinition = "VARCHAR(3)")
    private String amountTransactionCurrencyCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="debit_account", referencedColumnName = "account_id", columnDefinition = "INT(10)")
    private Account sourceAccount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="credit_account", referencedColumnName = "account_id", columnDefinition = "INT(10)")
    private Account destinationAccount;

    @Column(name = "result_code",columnDefinition = "VARCHAR(6)")
    private String resultCode;

    @Transient
    private String sourceAccountNumber;

    @Transient
    private String destinationAccountNumber;
}
