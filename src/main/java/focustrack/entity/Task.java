package focustrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String priority = "MEDIUM";

    @Column
    private String status = "PENDING";

    @Column
    private String category;

    @Column
    private LocalDate dueDate;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}