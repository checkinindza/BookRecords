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

    public Admin(int id, String login, String password, String name, String surname, String phoneNum) {
        super(id, login, password, name, surname);
        this.phoneNum = phoneNum;
    }

    public Admin(String login, String password, String name, String surname, String phoneNum) {
        super(login, password, name, surname);
        this.phoneNum = phoneNum;
    }
}
