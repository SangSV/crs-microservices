package vn.edu.crs.registration_service.repository;

import java.util.List;
import vn.edu.crs.registration_service.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Registration> findByStudentId(Long studentId);
}
