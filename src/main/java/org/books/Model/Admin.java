package org.books.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Admin extends User {
    private String phoneNum;

    public Admin(String login, String password, String name, String surname, String email, String phoneNum) {
        super(login, password, name, surname, email);
        this.phoneNum = phoneNum;
    }

    public void setPhone(String text) {
        this.phoneNum = text;
    }
}
