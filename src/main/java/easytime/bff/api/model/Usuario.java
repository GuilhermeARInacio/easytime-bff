package easytime.bff.api.model;

import lombok.Getter;

@Getter
public class Usuario {
    private Long id;
    private String login;
    private String password;

    public Usuario(){}

    public Usuario(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
