package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.GroupRequest;
import peaksoft.spring_boot_rest_api.dto.GroupResponse;
import peaksoft.spring_boot_rest_api.dto.GroupResponseView;
import peaksoft.spring_boot_rest_api.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
@Tag(name = "Group Auth", description = "We can create new Group")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class GroupController {
    private final GroupService groupService;

    @GetMapping()
    @Operation(summary = "Get all Groups", description = "Only Admin and Instructor get all Groups")
    public List<GroupResponse> getAllGroups() {
        System.out.println("Controller");
        return groupService.getAllGroups();
    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin and Instructor  can get Group by id")
    public GroupResponse getGroupById(@PathVariable("id") Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    @Operation(summary = "create",description = "Admin and Instructor can create new Group")
    public GroupResponse saveGroup(@RequestBody GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @PutMapping("{id}")
    @Operation(summary = "update",description = "Admin and Instructor can create new Group")
    public GroupResponse updateGroup(@PathVariable("id") Long id, @RequestBody GroupRequest groupRequest) {
        return groupService.updateGroup(id,groupRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Group by id")
    public String deleteGroup(@PathVariable("id") Long id) {
       groupService.deleteGroup(id);
        return "Successfully deleted group with id : " + id;
    }

    @GetMapping("search")
    public GroupResponseView getAllGroups(@RequestParam(name = "text", required = false)
                                              String text, @RequestParam int page,
                                              @RequestParam int size) {
        return groupService.searchAndPagination(text,page,size);
    }

}
