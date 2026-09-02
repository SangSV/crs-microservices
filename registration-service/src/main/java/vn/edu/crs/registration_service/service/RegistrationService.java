package vn.edu.crs.registration_service.service;

import vn.edu.crs.registration_service.client.CourseClient;
import vn.edu.crs.registration_service.dto.RegistrationRequestDTO;
import vn.edu.crs.registration_service.entity.Registration;
import vn.edu.crs.registration_service.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseClient courseClient;

    @Transactional
    public Registration register(RegistrationRequestDTO dto) {
        // 1. Kiểm tra đã đăng ký chưa
        if (registrationRepository.existsByStudentIdAndCourseId(dto.getStudentId(), dto.getCourseId())) {
            throw new IllegalArgumentException("Sinh vien da dang ky mon hoc nay roi");
        }

        // 2. Gọi API nội bộ sang course-service để trừ chỗ
        courseClient.reserveSeat(dto.getCourseId());

        // 3. Lưu bản ghi đăng ký vào DB
        Registration registration = new Registration();
        registration.setStudentId(dto.getStudentId());
        registration.setCourseId(dto.getCourseId());
        return registrationRepository.save(registration);
    }

    @Transactional
    public void cancel(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Khong tim thay ban ghi dang ky id = " + id));

        // 1. Gọi API nội bộ sang course-service để hoàn chỗ
        courseClient.releaseSeat(registration.getCourseId());

        // 2. Xóa bản ghi đăng ký trong DB
        registrationRepository.delete(registration);
    }

    public java.util.List<Registration> getMyRegistrations(Long studentId) {
        return registrationRepository.findByStudentId(studentId);
    }
}
