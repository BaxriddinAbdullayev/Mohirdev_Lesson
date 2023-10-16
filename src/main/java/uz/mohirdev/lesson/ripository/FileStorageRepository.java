package uz.mohirdev.lesson.ripository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mohirdev.lesson.entity.FileStorage;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage,Long> {
}
