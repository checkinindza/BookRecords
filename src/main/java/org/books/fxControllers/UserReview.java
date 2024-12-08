package org.books.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.books.Model.Client;
import org.books.Model.Comment;
import org.books.Model.User;
import org.books.hibernateControllers.CustomHibernate;

public class UserReview {
    @FXML
    public TreeView<Comment> userReview;
    @FXML
    public TextArea commentBody;
    @FXML
    public TextField commentTitle;
    @FXML
    public Button updateButton;
    @FXML
    public ContextMenu commentContextMenu;
    @FXML
    public MenuItem deleteItem;

    private User currentUser;
    private Client targetClient;
    private CustomHibernate hibernate;

    public void setData(EntityManagerFactory entityManagerFactory, User user, Client client) {
        this.currentUser = user;
        this.hibernate = new CustomHibernate(entityManagerFactory);
        this.targetClient = client;
        fillTree();

        if (currentUser instanceof Client) {
            updateButton.setDisable(true);
            commentContextMenu.hide();
            //deleteItem.setDisable(true);
        }
    }

    private void fillTree() {
        userReview.setRoot(new TreeItem<>());
        userReview.setShowRoot(false);
        userReview.getRoot().setExpanded(true);
        Client clientFromDb = hibernate.getEntityById(Client.class, targetClient.getId());
        clientFromDb.getCommentList().forEach(c->addTreeItem(c, userReview.getRoot()));
    }

    public void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub->addTreeItem(sub, treeItem));
    }

    public void insertComment() {
        if (currentUser instanceof Client client) {

            Comment selectedComment = userReview.getSelectionModel().getSelectedItem() != null ? userReview.getSelectionModel().getSelectedItem().getValue() : null;
            Comment comment;
            if (selectedComment != null) {
                comment = new Comment(commentTitle.getText(), commentBody.getText(), selectedComment, client);
            } else {
                comment = new Comment(commentTitle.getText(), commentBody.getText(), targetClient, client);
            }
            hibernate.create(comment);
            fillTree();
        }
    }

    public void loadComment() {
        Comment selectedComment = userReview.getSelectionModel().getSelectedItem().getValue();
        commentTitle.setText(selectedComment.getTitle());
        commentBody.setText(selectedComment.getCommentBody());
    }

    public void updateComment() {
        Comment selectedComment = userReview.getSelectionModel().getSelectedItem().getValue();
        selectedComment.setTitle(commentTitle.getText());
        selectedComment.setCommentBody(commentBody.getText());
        hibernate.update(selectedComment);
        fillTree();
    }

    public void deleteComment() {
        hibernate.deleteComment(userReview.getSelectionModel().getSelectedItem().getValue().getId());
    }
}
