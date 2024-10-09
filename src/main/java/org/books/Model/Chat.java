package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Book book;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Comment> messages;

    public Chat(Book book, List<Comment> messages) {
        this.book = book;
        this.messages = messages;
    }
}
