package tek.getarrays.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tek.getarrays.employeemanagement.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    User findByEmail(String email);

    Optional<User> findByUserNameOrEmail(String userName, String email);


}
