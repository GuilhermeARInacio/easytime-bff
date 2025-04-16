package easytime.bff.api.model;

public class Usuario {
    private Long id;
    private String usuario;
    private String senha;

    public Usuario(){}

    public Usuario(String usuario, String senha) {
    }

    // @Override
    public String getPassword(){
        return senha;
    }

    // @Override
    public String getUsername(){
        return usuario;
    }
}
