package focustrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String category;

    @Column
    private LocalDate targetDate;

    @Column
    private int progress = 0;

    @Column
    private String status = "ACTIVE";

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}