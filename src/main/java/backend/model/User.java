package backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Setter
@Getter
public class User {

    @Id
    @NotNull
    @Size(min=1, max=30)
    private String login;
    @NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    private String token;
    @NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(length = 1000)
    private String password;
    @NotNull
    @Size(min=1, max=50)
    private String email;
    @NotNull
    @Size(min=6, max=15)
    private String telephone;
    @NotNull
    @Size(min=1, max=50)
    private String firstName;
    @NotNull
    @Size(min=1, max=50)
    private String lastName;
    @NotNull
    private String type;
    @NotNull
    private Boolean enabled;
}
