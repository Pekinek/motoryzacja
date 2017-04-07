package backend.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Token {

    private String token;
    private String login;
    private String type;
    
    public Token(){
    }

    public Token(String token, String login, String type){
        this.token = token;
        this.login = login;
        this.type = type;
    }
}
