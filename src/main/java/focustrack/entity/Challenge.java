package focustrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private LocalDate targetDate;  // Optional due/end date

    @Column
    private int progress = 0;

    @Column
    private String status = "ACTIVE";  // ACTIVE, COMPLETED, FAILED

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}