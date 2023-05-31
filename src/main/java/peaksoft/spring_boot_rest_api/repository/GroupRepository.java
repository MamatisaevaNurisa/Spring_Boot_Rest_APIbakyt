package peaksoft.spring_boot_rest_api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.spring_boot_rest_api.entity.Group;

import java.util.List;
@Repository
public interface GroupRepository  extends JpaRepository<Group,Long> {


    @Query("select  g from Group g where  upper(g.groupName) like  concat('%',:text,'%')" +
            "or upper(g.dateOfStart)like  concat('%',:text,'%')or upper(g.dateOfFinish)like  concat('%',:text,'%')")
    List<Group>searchAndPagination(@Param("text")String  text, Pageable pageable);


}
