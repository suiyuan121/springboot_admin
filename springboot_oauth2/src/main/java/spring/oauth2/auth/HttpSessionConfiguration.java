package spring.oauth2.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created by Jerry on 2017/6/27.
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class HttpSessionConfiguration {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        //cookieSerializer.setDomainName("fuwo.com");
        cookieSerializer.setCookieName("AUTH-SESSION");
        cookieSerializer.setCookieMaxAge(600);
        return cookieSerializer;
    }
}
