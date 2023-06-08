package peaksoft.spring_boot_rest_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.spring_boot_rest_api.dto.StudentRequest;
import peaksoft.spring_boot_rest_api.dto.StudentResponse;
import peaksoft.spring_boot_rest_api.dto.StudentResponseView;
import peaksoft.spring_boot_rest_api.entity.Group;
import peaksoft.spring_boot_rest_api.entity.Role;
import peaksoft.spring_boot_rest_api.entity.StudyFormation;
import peaksoft.spring_boot_rest_api.entity.User;
import peaksoft.spring_boot_rest_api.repository.GroupRepository;
import peaksoft.spring_boot_rest_api.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<StudentResponse> getAllStudents() {
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.STUDENT) {
                studentResponses.add(mapToResponse(user));
            }
        }
        return studentResponses;
    }

    public StudentResponse creatStudent(StudentRequest studentRequest) {
        Group group = groupRepository.findById(studentRequest.getGroupId()).get();
        User user = new User();
        user.setFirstName(studentRequest.getFirstName());
        user.setLastName(studentRequest.getLastName());
        user.setEmail(studentRequest.getEmail());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setStudyFormation(StudyFormation.valueOf(studentRequest.getStudyFormat()));
        user.setRole(Role.STUDENT);
        user.setLocalDate(LocalDate.now());
        List<User> students = new ArrayList<>();
        user.setGroup(group);
        group.setStudents(students);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public StudentResponse mapToResponse(User user) {
        return StudentResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleName(String.valueOf(Role.STUDENT))
                .studyFormat(user.getStudyFormation().toString())
                .isActivity(user.getIsActive())
                .isDelete(user.getIsDeleted())
                .localDate(user.getLocalDate())
                .group(user.getGroup()).build();
    }

    public StudentResponse getStudentById(Long id) {
        User user = userRepository.findById(id).get();
        if (!id.equals(user.getId()) && user.getRole() == Role.STUDENT) {
            log.error("not found");
            throw new UsernameNotFoundException("Not found");

        } else {
            return mapToResponse(user);
        }
    }

    public StudentResponse updateStudent(Long studentId, StudentRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).get();
        User student = userRepository.findById(studentId).get();
        List<User>students=new ArrayList<>();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setRole(Role.STUDENT);
        student.setStudyFormation(StudyFormation.valueOf(request.getStudyFormat()));
        students.add(student);
        student.setGroup(group);
        group.setStudents(students);
        userRepository.save(student);
        return mapToResponse(student);
    }


    public void delete(Long studentId) {
        User user = userRepository.findById(studentId).get();
        if (!(studentId.equals(user.getId())) && user.getRole().equals(Role.STUDENT)) {
            log.error("Not found");


        } else {
            userRepository.delete(user);
        }
    }

    public StudentResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        StudentResponseView studentResponseView = new StudentResponseView();
        studentResponseView.setStudentResponses(view(search(text, pageable)));
        return studentResponseView;

    }


    public List<StudentResponse> view(List<User> users) {
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (User user : users) {
            studentResponses.add(mapToResponse(user));

        }
        return studentResponses;
    }

    private List<User> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        List<User> users = userRepository.searchAndPagination(name.toUpperCase(), pageable);
        List<User> students = new ArrayList<>();
        for (User user : users) {
            if (user.getRole() == Role.STUDENT) {
                students.add(user);
            }
        }
        return students;


    }
}