package rafikibora.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(length = 100, unique = true)
    @NotNull
    private String roleName;

    // @JsonIgnore
    // @ToString.Exclude
    // @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private Set<User> users;

    // public GrantedAuthority grantedAuthority() {
    //     return new SimpleGrantedAuthority(this.roleName);
    // }

}
