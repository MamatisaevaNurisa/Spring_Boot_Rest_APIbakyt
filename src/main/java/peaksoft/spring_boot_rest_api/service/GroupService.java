package peaksoft.spring_boot_rest_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.spring_boot_rest_api.dto.GroupRequest;
import peaksoft.spring_boot_rest_api.dto.GroupResponse;
import peaksoft.spring_boot_rest_api.dto.GroupResponseView;
import peaksoft.spring_boot_rest_api.entity.Course;
import peaksoft.spring_boot_rest_api.entity.Group;
import peaksoft.spring_boot_rest_api.repository.CourseRepository;
import peaksoft.spring_boot_rest_api.repository.GroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    private final CourseRepository courseRepository;

    public List<GroupResponse> getAllGroups() {
        List<GroupResponse> groupResponses = new ArrayList<>();
        for (Group group : groupRepository.findAll()) {
            groupResponses.add(mapToResponse(group));
        }
        return groupResponses;
    }

    public GroupResponse mapToResponse(Group group) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setGroupName(group.getGroupName());
        groupResponse.setDateOfStart(group.getDateOfStart());
        groupResponse.setDateOfFinish(group.getDateOfFinish());
        groupResponse.setLocalDate(group.getLocalDate());
        groupResponse.setIsActivity(group.getIsActive());
        groupResponse.setIsDelete(group.getIsDeleted());
        List<Course> courses = group.getCourses();
        groupResponse.setCourses(courses);
        System.out.println("Im Courses");
        return groupResponse;
    }


    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id).get();
        return mapToResponse(group);
    }

    public GroupResponse saveGroup(GroupRequest groupRequest) {
        Group group = new Group();
        group.setId(group.getId());
        group.setGroupName(groupRequest.getGroupName());
        group.setDateOfStart(groupRequest.getDateOfStart());
        group.setDateOfFinish(groupRequest.getDateOfFinish());
        group.setIsActive(group.getIsActive());
        group.setIsDeleted(group.getIsDeleted());
        group.setLocalDate(LocalDate.now());
        Course course = courseRepository.findById(groupRequest.getCourseId()).get();
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        group.setCourses(courses);
        course.setGroups(groups);
        groupRepository.save(group);
        return mapToResponse(group);
    }

    public GroupResponse updateGroup(Long groupId, GroupRequest groupRequest) {
        Group group = groupRepository.findById(groupId).get();
        group.setGroupName(groupRequest.getGroupName());
        group.setDateOfStart(groupRequest.getDateOfStart());
        group.setDateOfFinish(groupRequest.getDateOfFinish());
        group.setLocalDate(LocalDate.now());
            List<Group> groups1 = new ArrayList<>();
            groups1.add(group);
            List<Course> courses1 = new ArrayList<>();
            Course course = courseRepository.findById(groupRequest.getCourseId()).get();
            courses1.add(course);
            group.setCourses(courses1);
            course.setGroups(groups1);
        groupRepository.save(group);
        return mapToResponse(group);

    }


    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);

    }

    public GroupResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        GroupResponseView groupResponseView = new GroupResponseView();
        groupResponseView.setGroupResponses(view(search(text, pageable)));
        return groupResponseView;

    }

    public List<GroupResponse> view(List<Group> groups) {
        List<GroupResponse> groupResponses = new ArrayList<>();
        for (Group group : groups) {
            groupResponses.add(mapToResponse(group));

        }
        return groupResponses;
    }

    private List<Group> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return groupRepository.searchAndPagination(text, pageable);
    }
}