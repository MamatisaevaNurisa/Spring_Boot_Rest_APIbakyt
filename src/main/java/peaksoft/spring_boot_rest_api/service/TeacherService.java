package peaksoft.spring_boot_rest_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.spring_boot_rest_api.dto.TeacherRequest;
import peaksoft.spring_boot_rest_api.dto.TeacherResponse;
import peaksoft.spring_boot_rest_api.dto.TeacherResponseView;
import peaksoft.spring_boot_rest_api.entity.Course;
import peaksoft.spring_boot_rest_api.entity.Role;
import peaksoft.spring_boot_rest_api.entity.User;
import peaksoft.spring_boot_rest_api.repository.CourseRepository;
import peaksoft.spring_boot_rest_api.repository.UserRepository;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public List<TeacherResponse> getAllTeachers() {
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.INSTRUCTOR) {
                teacherResponses.add(mapToResponse(user));
            }
        }
        return teacherResponses;
    }

    public TeacherResponse creatTeacher(TeacherRequest teacherRequest) {
        Course course = courseRepository.findById(teacherRequest.getCourseId()).get();
        if (course.getTeacher() != null) {
            log.error("Course all ready exists Teacher is:  " + course.getTeacher().getFirstName());
            throw new EntityExistsException("Course all ready exists!");
        }
        User user = new User();
        user.setFirstName(teacherRequest.getFirstName());
        user.setLastName(teacherRequest.getLastName());
        user.setEmail(teacherRequest.getEmail());
        user.setPassword(passwordEncoder.encode(teacherRequest.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        user.setLocalDate(LocalDate.now());
        course.setTeacher(user);
        user.setCourse(course);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public TeacherResponse mapToResponse(User user) {
        return TeacherResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isActivity(user.getIsActive())
                .isDelete(user.getIsDeleted())
                .roleName(user.getRole().name())
                .course(user.getCourse())
                .localDate(user.getLocalDate()).build();
    }

    public TeacherResponse getTeacherById(Long id) {
        User user = userRepository.findById(id).get();
        if (!id.equals(user.getId()) && user.getRole() == Role.INSTRUCTOR) {
            log.error("not found");
            throw new UsernameNotFoundException("Not found");

        } else {
            return mapToResponse(user);
        }
    }

    public TeacherResponse updateTeacher(Long teacherId, TeacherRequest request) {
        Course course = courseRepository.findById(request.getCourseId()).get();
        User user = userRepository.findById(teacherId).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        course.setTeacher(user);
        user.setCourse(course);
        user.setLocalDate(LocalDate.now());
        course.setTeacher(user);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public void deleteTeacher(Long id) {
        User teacher = userRepository.findById(id).get();
        if (!id.equals(teacher.getId()) && teacher.getRole().equals(Role.INSTRUCTOR)) {
            log.error("User not found!");
        } else {
            teacher.removeCourse();
            userRepository.delete(teacher);
        }

    }

    public TeacherResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        TeacherResponseView teacherResponseView = new TeacherResponseView();
        teacherResponseView.setTeacherResponses(view(search(text, pageable)));
        return teacherResponseView;

    }


    public List<TeacherResponse> view(List<User> users) {
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (User user : users) {
            teacherResponses.add(mapToResponse(user));

        }
        return teacherResponses;
    }

    private List<User> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        List<User> users = userRepository.searchAndPagination(name.toUpperCase(), pageable);
        List<User> instructors = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == Role.INSTRUCTOR) {
                instructors.add(user);
            }

        }
        return instructors;
    }
}
