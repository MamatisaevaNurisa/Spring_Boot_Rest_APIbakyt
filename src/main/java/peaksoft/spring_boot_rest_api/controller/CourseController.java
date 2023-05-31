package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.CourseRequest;
import peaksoft.spring_boot_rest_api.dto.CourseResponse;
import peaksoft.spring_boot_rest_api.dto.CourseResponseView;
import peaksoft.spring_boot_rest_api.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Tag(name = "Course Auth", description = "We can create new  Course")
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
public class CourseController {
    private final CourseService courseService;

    @GetMapping()
    @Operation(summary = "Get all courses", description = "Only Admin and Instructor get all courses")
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin and Instructor  can get Course by id")
    public CourseResponse getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @Operation(summary = "create",description = "Admin and Instructor can create new Course")
    public CourseResponse saveCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.saveCourse(courseRequest);
    }

    @PutMapping("{id}")
    @Operation(summary = "create",description = "Admin and Instructor can create new Course")
    public CourseResponse update(@PathVariable("id") Long id, @RequestBody CourseRequest courseRequest) {
        return courseService.updateCourse(id, courseRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Course by id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return "Successfully delete" + id;
    }
    @GetMapping("search")
    public CourseResponseView getAllCompanies(@RequestParam(name = "text", required = false)
                                              String text, @RequestParam int page,
                                              @RequestParam int size) {
        return courseService.searchAndPagination(text, page, size);
    }
}

