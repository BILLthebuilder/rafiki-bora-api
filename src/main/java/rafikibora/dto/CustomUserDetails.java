package rafikibora.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import rafikibora.model.users.Roles;
import rafikibora.model.users.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

 @Data
 public class CustomUserDetails implements UserDetails {
     private final User user;

     public CustomUserDetails(User user) {
         this.user = user;
     }

    //  @Override
    //  public Collection<? extends GrantedAuthority> getAuthorities() {
    //      return user.getRoles().stream().map(Roles::grantedAuthority).collect(Collectors.toList());
    //  }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }

     @Override
     public String getPassword() {
         return user.getPassword();
     }

     @Override
     public String getUsername() {
         return user.getEmail();
     }

     @Override
     public boolean isAccountNonExpired() {
         return true;
     }

     @Override
     public boolean isAccountNonLocked() {
         return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
         return true;
     }

     @Override
     public boolean isEnabled() {
         return user.isStatus();
     }
 }
