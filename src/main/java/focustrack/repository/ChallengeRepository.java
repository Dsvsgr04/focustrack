package focustrack.repository;

import focustrack.entity.Challenge;
import focustrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUser(User user);
    List<Challenge> findByUserAndStatus(User user, String status);
}