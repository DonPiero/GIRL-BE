package com.example.girlbe.model;

import com.example.girlbe.model.enums.Rank;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="Users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profilePicture")
    private String profilePicture;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "rank")
    private Rank rank;

    @Column(name = "ongoingExp")
    private Integer ongoingExp;

    @Column(name = "completedExp")
    private Integer completedExp;

    @Column(unique = true, name = "email")
    @NonNull
    private String email;

    @Column(name = "prefix")
    private String prefix;

    @Column(unique = true, name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Experiment> experiments;

    public void addExperiment(Experiment experiment) {this.experiments.add(experiment); this.ongoingExp++;}

    public void removeExperiment(Experiment experiment) {this.experiments.remove(experiment);}

    public void completeExperiment(){/*this.ongoingExp--; */this.completedExp++;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return email.toString();
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
}
