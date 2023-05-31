package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.StudentRequest;
import peaksoft.spring_boot_rest_api.dto.StudentResponse;
import peaksoft.spring_boot_rest_api.dto.StudentResponseView;
import peaksoft.spring_boot_rest_api.service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
@Tag(name = "Student Auth", description = "We can create new Student")
@PreAuthorize("hasAuthority('ADMIN')")
public class StudentController {
    private final StudentService studentService;

        @GetMapping()
        @Operation(summary = "Get all Student", description = "Only Admin and Instructor get all Student")
        public List<StudentResponse> getAll() {
            System.out.println("STUDENT CONTROLLER");
            return studentService.getAllStudents();

        }

        @GetMapping("{id}")
        @Operation(summary = "Get by id", description = "Admin and Instructor  can get Student by id")
        public StudentResponse getById(@PathVariable("id") Long studentId) {
            return studentService.getStudentById(studentId);
        }

        @PostMapping("addStudent")
        @Operation(summary = "Create",description = "Admin and Instructor can create new Student")
        public StudentResponse createStudent(@RequestBody StudentRequest request) {
            return studentService.creatStudent(request);

        }

        @PutMapping("{id}")
        @Operation(summary = "Update",description = "Admin and Instructor can update new Group")
        public StudentResponse update(@PathVariable("id") Long studentId, @RequestBody StudentRequest request) {
            return studentService.updateStudent(studentId, request);

        }


        @DeleteMapping("{id}")
        @Operation(summary = "Delete",description = "Admin can delete Student by id")
        @PreAuthorize("hasAuthority('ADMIN')")
        public String delete(@PathVariable("id") Long userId) {
            return studentService.delete(userId);
        }

    @GetMapping("search")
    public StudentResponseView getAllStudents(@RequestParam(name = "text",required = false)String text,
                                               @RequestParam int page,
                                               @RequestParam int size) {
        return studentService.searchAndPagination(text, page, size);

    }
}