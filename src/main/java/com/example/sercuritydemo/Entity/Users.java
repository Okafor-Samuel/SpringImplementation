package com.example.sercuritydemo.Entity;

import com.example.sercuritydemo.Enum.Gender;
import com.example.sercuritydemo.Enum.Role;
import com.example.sercuritydemo.Payload.RequestDto.UsersDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @NaturalId(mutable = true)
    private String email;
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Invalid password format")
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(this.role.name()));
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email ;
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
        return true;
    }
    public Users (UsersDto usersDto){
        this.firstName = usersDto.getFirstName();
        this.lastName = usersDto.getLastName();
        this.email = usersDto.getEmail();
        this.password = new BCryptPasswordEncoder().encode(usersDto.getPassword());
        this.phoneNumber = usersDto.getPhoneNumber();
        this.role = Role.valueOf(String.valueOf(usersDto.getRole()).toUpperCase());
        this.gender = Gender.valueOf(String.valueOf(usersDto.getGender()));

    }
}
