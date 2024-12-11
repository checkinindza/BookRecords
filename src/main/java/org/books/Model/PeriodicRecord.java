package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.books.Model.enums.PublicationStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PeriodicRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Publication publication;
    private LocalDate transactionDate;
    private LocalDate returnDate;
    private PublicationStatus status;

    public PeriodicRecord(User user, Publication publication, LocalDate transactionDate, LocalDate returnDate, PublicationStatus status) {
        this.user = user;
        this.publication = publication;
        this.transactionDate = transactionDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public PeriodicRecord(User user, Publication publication, LocalDate transactionDate, PublicationStatus status) {
        this.user = user;
        this.status = status;
        this.publication = publication;
        this.transactionDate = transactionDate;
    }
}
