package vn.edu.crs.registration_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "trang_thai")
    private String trangThai = "DA_DANG_KY";

    @Column(name = "ngay_dang_ky")
    private java.time.LocalDateTime ngayDangKy;

    @PrePersist
    public void prePersist() {
        if (this.trangThai == null) {
            this.trangThai = "DA_DANG_KY";
        }
        if (this.ngayDangKy == null) {
            this.ngayDangKy = java.time.LocalDateTime.now();
        }
    }
}
