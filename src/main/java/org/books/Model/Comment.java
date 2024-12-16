package org.books.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String commentBody;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Chat chat;
    @ManyToOne
    private Client commentOwner;

    public Comment(String title, String commentBody, LocalDateTime timestamp, List<Comment> replies, Comment parentComment, Client client) {
        this.title = title;
        this.commentBody = commentBody;
        this.timestamp = timestamp;
        this.replies = replies;
        this.parentComment = parentComment;
        this.client = client;
    }

    public Comment(String title, String commentBody, Client commentOwner, Client client) {
        this.title = title;
        this.commentBody = commentBody;
        this.commentOwner = commentOwner;
        this.client = client;
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String title, String commentBody, Comment parentComment, Client commentOwner) {
        this.title = title;
        this.commentBody = commentBody;
        this.parentComment = parentComment;
        this.commentOwner = commentOwner;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return commentOwner.name + " " + commentOwner.surname + " " + timestamp;
    }
}
