package peaksoft.spring_boot_rest_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.spring_boot_rest_api.dto.CourseRequest;
import peaksoft.spring_boot_rest_api.dto.CourseResponse;
import peaksoft.spring_boot_rest_api.dto.CourseResponseView;
import peaksoft.spring_boot_rest_api.entity.Company;
import peaksoft.spring_boot_rest_api.entity.Course;
import peaksoft.spring_boot_rest_api.repository.CompanyRepository;
import peaksoft.spring_boot_rest_api.repository.CourseRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;


    private final CompanyRepository companyRepository;

    public List<CourseResponse> getAllCourses() {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courseResponses.add(mapToResponse(course));

        }
        return courseResponses;
    }


    public CourseResponse getCourseById(Long id) {

        Course course = courseRepository.findById(id).get();

        return mapToResponse(course);

    }

    public CourseResponse saveCourse(CourseRequest request) {
      Course course=mapToEntity(request);
        Company company = companyRepository.findById(request.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);

        return mapToResponse(course);
    }

    public Course mapToEntity(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        course.setLocalDate(LocalDate.now());
        course.setIsActive(course.getIsActive());
        course.setIsDeleted(course.getIsDeleted());
        return course;
    }
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id).get();
        course.setCourseName(courseRequest.getCourseName());
        course.setDurationMonth(courseRequest.getDurationMonth());
    if (courseRequest.getCompanyId() != null) {
            Company company = companyRepository.findById(courseRequest.getCompanyId()).get();
            course.setCompany(company);
        }
        courseRepository.saveAndFlush(course);
        return mapToResponse(course);

    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }



    public CourseResponse mapToResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        courseResponse.setCompany(course.getCompany());
        courseResponse.setIsActivity(course.getIsActive());
        courseResponse.setIsDelete(course.getIsDeleted());
        courseResponse.setLocalDate(course.getLocalDate());
        return courseResponse;
    }

    public CourseResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CourseResponseView courseResponseView = new CourseResponseView();
        courseResponseView.setCourseResponses(view(search(text, pageable)));
        return courseResponseView;
    }

    public List<CourseResponse> view(List<Course> courses) {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course : courses) {
            courseResponses.add(mapToResponse(course));


        }
        return courseResponses;
    }

    private List<Course> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return courseRepository.searchAndPagination(name, pageable);
    }
}

