package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.TeacherRequest;
import peaksoft.spring_boot_rest_api.dto.TeacherResponse;
import peaksoft.spring_boot_rest_api.dto.TeacherResponseView;
import peaksoft.spring_boot_rest_api.service.TeacherService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Auth", description = "We can create new Teacher")
@PreAuthorize("hasAuthority('ADMIN')")

public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "Get all Student", description = "Only Admin and Instructor get all Student")
    public List<TeacherResponse> getAll() {
        return teacherService.getAllTeachers();

    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin and Instructor  can get Student by id")
    public TeacherResponse getById(@PathVariable("id") Long teacherId) {
        return teacherService.getTeacherById(teacherId);
    }

    @PostMapping("addTeacher")
    @Operation(summary = "Create",description = "Admin and Instructor can create new Student")
    public TeacherResponse createTeacher(@RequestBody TeacherRequest request) {
        return teacherService.creatTeacher(request);

    }

    @PutMapping("{id}")
    @Operation(summary = "Update",description = "Admin and Instructor can update new Group")
    public TeacherResponse update(@PathVariable("id") Long teacherId, @RequestBody TeacherRequest request) {
        return teacherService.updateTeacher(teacherId, request);

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Student by id")
    public String delete(@PathVariable("id") Long teacherId) {
        teacherService.deleteTeacher(teacherId);

        return "Successfully deleted Teacher with id : "+teacherId;
    }

    @GetMapping("search")
    public TeacherResponseView getAllCompanies(@RequestParam(name = "text", required = false) String text,
                                               @RequestParam int page,
                                               @RequestParam int size) {
        return teacherService.searchAndPagination(text,page,size) ;

    }
}
