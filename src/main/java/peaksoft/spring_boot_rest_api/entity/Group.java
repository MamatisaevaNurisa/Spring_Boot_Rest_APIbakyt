package peaksoft.spring_boot_rest_api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "date_of_start")
    private String dateOfStart;
    @Column(name = "date_of_finish")
    private String dateOfFinish;
    @CreatedDate
    private LocalDate localDate;
    private  Boolean isActive=true;
    private  Boolean isDeleted=false;
    @Transient
    private Long courseId;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST})
    @JoinTable(name = "groups_courses",
            joinColumns = @JoinColumn(name = "groups_id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id"))
    private List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    @JsonIgnore
    private List<User> students;

}