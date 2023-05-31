package peaksoft.spring_boot_rest_api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.spring_boot_rest_api.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select  u from User u where  upper(u.firstName) like  concat('%',:text,'%')" +
            "or  upper(u.lastName) like  concat('%',:text,'%')" +
            "or  upper(u.role) like  concat('%',:text,'%')" +
            "or  upper(u.studyFormation) like  concat('%',:text,'%')")
    List<User>searchAndPagination(@Param("text")String  text, Pageable pageable);


//    @Query("select  s from User s where s.role='STUDENT'")
//    List<User>findAllStudent();

    // Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);

}