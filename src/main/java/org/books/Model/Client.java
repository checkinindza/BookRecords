package org.books.Model;

import jakarta.persistence.*;
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

public class Client extends User {
    private String address;
    private LocalDate birthDate;
    private String clientBio;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Comment> commentList;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Publication> ownedPublications;
    @OneToMany(mappedBy = "borrowerClient", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Publication> borrowedPublications;
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Comment> myComments;

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
}
