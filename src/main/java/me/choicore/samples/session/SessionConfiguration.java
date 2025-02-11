package me.choicore.samples.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.jackson2.SecurityJackson2Modules;


@Slf4j
@Configuration(proxyBeanMethods = false)
public class SessionConfiguration implements BeanClassLoaderAware {
    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(@Nonnull final ClassLoader classLoader) {
        log.trace("BeanClassLoader injected: {}", classLoader.getClass().getName());
        this.classLoader = classLoader;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(@Nonnull final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder
                .modules(SecurityJackson2Modules.getModules(this.classLoader))
                .build();

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
