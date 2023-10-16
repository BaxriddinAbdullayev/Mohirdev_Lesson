package uz.mohirdev.lesson.ripository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.mohirdev.lesson.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("select e from Employee e where e.name= :name and e.lastName= :lastName")
    List<Employee> findAll(@Param("name") String name,
                           @Param("lastName") String lastName);

    List<Employee> findAllByNameAndLastName(String name,String lastName);

    @Query(value = "select * from employee e where e.last_name= :name",nativeQuery = true)
    List<Employee> findAll(@Param("name") String name);

    List<Employee> findAllByNameLike(String name);

    @Query("select e from Employee  e where e.name like :name")
    List<Employee> findAllByNameLikeJPQL(@Param("name") String name);

    @Query(value = "select * from employee e where e.name like :name",nativeQuery = true)
    List<Employee> findAllByNameLikeNative(@Param("name") String name);
}
