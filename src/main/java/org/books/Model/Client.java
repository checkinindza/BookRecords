package org.books.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Client extends User implements Comparable<User> {
    private String address;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Comment> commentList;
    @Transient
    private List<Publication> ownedPublications;
    @Transient
    private List<Publication> borrowedPublications;
    // daugiau jeigu reikia

    public Client(String login, String password, String name, String surname, String email, String address, LocalDate birthDate, List<Comment> commentList, List<Publication> ownedPublications, List<Publication> borrowedPublications) {
        super(login, password, name, surname, email);
        this.address = address;
        this.birthDate = birthDate;
        this.commentList = commentList;
        this.ownedPublications = ownedPublications;
        this.borrowedPublications = borrowedPublications;
    }

    public Client(String login, String password, String name, String surname, String email, LocalDate birthDate, String address) {
        super(login, password, name, surname, email);
        this.address = address;
        this.birthDate = birthDate;
    }

    @Override
    public int compareTo(User o) {
        /*if (this.login ? o.getLogin()) {
            return -1;
        }*/
        return -1;
    }
}
