package easytime.bff.api.model;

import lombok.Getter;

public class Usuario {
    private Long id;
    private String usuario;
    private String senha;

    // @Override
    public String getPassword(){
        return senha;
    }

    // @Override
    public String getUsername(){
        return usuario;
    }
}
