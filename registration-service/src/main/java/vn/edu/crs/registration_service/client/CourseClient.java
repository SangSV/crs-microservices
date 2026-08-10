package vn.edu.crs.registration_service.client;

import vn.edu.crs.registration_service.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CourseClient {

    private final RestTemplate restTemplate;

    @Value("${course-service.base-url}")
    private String baseUrl;

    /**
     * Gọi API nội bộ reserve-seat bên course-service để trừ chỗ
     */
    public CourseDTO reserveSeat(Long courseId) {
        String url = baseUrl + "/internal/courses/" + courseId + "/reserve-seat";
        return restTemplate.patchForObject(url, null, CourseDTO.class);
    }

    /**
     * Gọi API nội bộ release-seat bên course-service để hoàn chỗ
     */
    public CourseDTO releaseSeat(Long courseId) {
        String url = baseUrl + "/internal/courses/" + courseId + "/release-seat";
        return restTemplate.patchForObject(url, null, CourseDTO.class);
    }
}
