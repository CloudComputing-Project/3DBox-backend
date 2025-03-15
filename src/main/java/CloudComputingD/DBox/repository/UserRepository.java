package CloudComputingD.DBox.repository;

import CloudComputingD.DBox.entity.User;
import CloudComputingD.DBox.entity.embeddable.OauthId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthId(OauthId oauthId);

    @Query("SELECT u FROM User u WHERE u.oauthId.oauthServerId = :oauthServerId")
    User findByOauthServerId(String oauthServerId);
}