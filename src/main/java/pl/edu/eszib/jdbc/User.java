package pl.edu.eszib.jdbc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private int id;
    private String login;
    private String password;
    private Role role;

    public enum Role {
        ADMIN,
        USER
    }
}
