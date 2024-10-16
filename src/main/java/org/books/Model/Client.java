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
    private LocalDate birthDate;
    private String email;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Comment> commentList;
    @Transient
    private List<Publication> ownedPublications;
    @Transient
    private List<Publication> borrowedPublications;
    // daugiau jeigu reikia

    public Client(String login, String password, String name, String surname, String email, LocalDate birthDate) {
        super(login, password, name, surname);
        this.email = email;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public int compareTo(User o) {
        /*if (this.login ? o.getLogin()) {
            return -1;
        }*/
        return -1;
    }
}
