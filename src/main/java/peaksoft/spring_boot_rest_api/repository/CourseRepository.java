package peaksoft.spring_boot_rest_api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.spring_boot_rest_api.entity.Course;

import java.util.List;
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {



    @Query("select  co from Course co where  upper(co.courseName) like  concat('%',:text,'%')" +
            "or  upper(co.durationMonth) like  concat('%',:text,'%')")
    List<Course>searchAndPagination(@Param("text")String  text,Pageable pageable);

}
