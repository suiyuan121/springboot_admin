package spring.oauth2.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import spring.oauth2.auth.model.User;

/**
 * Created by Jerry on 2017/7/7.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
}
