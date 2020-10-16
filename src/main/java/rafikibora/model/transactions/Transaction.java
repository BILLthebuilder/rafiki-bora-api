package rafikibora.model.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafikibora.model.account.Account;
import rafikibora.model.terminal.Terminal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(columnDefinition = "VARCHAR(16)")
    @NotNull
    private String pan;

    @Column(name = "processing_code", updatable=false, columnDefinition = "VARCHAR(6)")
    @NotNull
    private String processingCode;

    @Column(name = "amount_transaction", updatable=false, columnDefinition = "DOUBLE(12,2)")
    @NotNull
    private double amountTransaction;

    @Column(name = "date_time_transmission", updatable=false, columnDefinition = "DATETIME")
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateTimeTransmission;

    @JsonIgnore
    @ManyToOne
    @NotNull
    @JoinColumn(name="terminal", referencedColumnName = "terminal_id")
    private Terminal terminal;

    @Column(name = "recipient_email", columnDefinition = "VARCHAR(30)")
    private String recipientEmail;

    @Column(name = "token", columnDefinition = "VARCHAR(9)")
    private String token;

    @Column(name = "currency_code", columnDefinition = "VARCHAR(3)")
    @NotNull
    private String currencyCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="debit_account", referencedColumnName = "account_id", columnDefinition = "INT(10)")
    private Account sourceAccount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="credit_account", referencedColumnName = "account_id", columnDefinition = "INT(10)")
    private Account destinationAccount;
}
