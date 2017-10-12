package spring.oauth2.auth.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.oauth2.auth.model.User;
import spring.oauth2.auth.service.repository.UserRepository;

/**
 * Created by Jerry on 2017/5/27.
 */
@CacheConfig(cacheNames = "users")
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @CachePut(key = "#user.id.toString()")
    @Override
    public User save(User user) {
        user = this.userRepository.save(user);
        return user;
    }

    @CachePut(key = "#user.id.toString()")
    @Override
    public User update(User user) {
        User u = this.userRepository.getOne(user.getId());
        BeanUtils.copyProperties(user, u, "id");
        return u;
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#id.toString()")
    @Override
    public User findById(Long id) {
        return this.userRepository.findOne(id);
    }
}
