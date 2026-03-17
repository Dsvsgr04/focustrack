package focustrack.service;

import focustrack.entity.Challenge;
import focustrack.entity.User;
import focustrack.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public Challenge createChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public List<Challenge> getUserChallenges(User user) {
        return challengeRepository.findByUser(user);
    }

    public List<Challenge> getUserChallengesByStatus(User user, String status) {
        return challengeRepository.findByUserAndStatus(user, status);
    }

    public Challenge getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Challenge not found!"));
    }

    public Challenge updateChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public void deleteChallenge(Long id) {
        challengeRepository.deleteById(id);
    }
}