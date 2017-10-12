package com.fuwo.b3d;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.concurrent.Executor;

/**
 * Created by Jerry on 2017/5/31.
 */
@Configuration
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class WebAppConfiguration {

    @Bean
    @Autowired
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }


    /*
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
    */

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(EntityManagerFactory entityManagerFactory) {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setDefaultMediaType(MediaType.APPLICATION_JSON);

                //Expose all IDs when using Spring Data Rest
                Metamodel metamodel = entityManagerFactory.getMetamodel();
                for (ManagedType<?> managedType : metamodel.getManagedTypes()) {
                    Class<?> javaType = managedType.getJavaType();
                    if (javaType.isAnnotationPresent(Entity.class)) {
                        config.exposeIdsFor(managedType.getJavaType());
                    }
                }
            }
        };
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("spring-tasks-");
        executor.initialize();
        return executor;
    }


    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config).build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
