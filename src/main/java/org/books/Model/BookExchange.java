package org.books.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class BookExchange {
    private List<User> allUsers;
    private List<Publication> allPublications;

    public BookExchange(List<Publication> allPublications, List<User> allUsers) {
        this.allPublications = allPublications;
        this.allUsers = allUsers;
    }

    public BookExchange() {
        this.allUsers = new ArrayList<>();
    }
}
