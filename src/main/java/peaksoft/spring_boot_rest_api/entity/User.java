package peaksoft.spring_boot_rest_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
    private Boolean isActive = true;
    private Boolean isDeleted = false;
    @CreatedDate
    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = {CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE})
    @JsonIgnore
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JsonIgnore
    private Group group;

    @Column(name = "study_format")
    @Enumerated(EnumType.STRING)
    private StudyFormation studyFormation;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        System.out.println("User");
        System.out.println(role.getAuthority());
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void removeCourse() {
        course.setTeacher(null);
        setCourse(null);
    }

    public void addCourse(Course course) {
        course.setTeacher(this);
        setCourse(course);
    }
}