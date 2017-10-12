package spring.oauth2.auth.service;

import spring.oauth2.auth.model.User;

/**
 * Created by Jerry on 2017/5/27.
 */
public interface UserService {

    User save(User user);

    User update(User user);

    User findById(Long id);

}
