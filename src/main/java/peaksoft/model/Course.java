package peaksoft.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Course name cant be null")
    @Column(length = 100000, name = "course_name")
    private String courseName;

    @Min(value = 1, message = "course duration should be more than 1 month")
    private int duration;

    @NotNull(message = "Course description cant be null")
    @Column(length = 100000, name = "description")
    private String description;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH, PERSIST}, fetch = FetchType.EAGER)
    private Company company;

    @ManyToMany(cascade = {MERGE, DETACH, REFRESH}, fetch = LAZY, mappedBy = "courses")
    private List<Group> groups;

    public void addGroup(Group group){
        if (groups==null){
            groups=new ArrayList<>();
        }
        groups.add(group);
    }

    @OneToMany(cascade = {ALL},fetch = LAZY, mappedBy = "course")
    private List<Instructor> instructors;

    public void addInstructors(Instructor instructor){
        if (instructors==null){
            instructors=new ArrayList<>();
        }
        instructors.add(instructor);
    }

    @OneToMany(cascade = {DETACH, PERSIST, REFRESH, MERGE}, fetch = LAZY, mappedBy = "course")
    private List<Lesson> lessons;

    public void addLesson(Lesson lesson){
        if (lessons==null){
            lessons=new ArrayList<>();
        }
        lessons.add(lesson);
    }



}
