package uz.mohirdev.lesson.ripository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mohirdev.lesson.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByLogin(String login);

    User findByLogin(String login);
}
