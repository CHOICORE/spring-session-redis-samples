package me.choicore.samples.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;


@Configuration(proxyBeanMethods = false)
public class SessionConfiguration implements BeanClassLoaderAware {
    private static final Logger log = LoggerFactory.getLogger(SessionConfiguration.class);

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(@Nonnull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(final ObjectMapper objectMapper) {
        ObjectMapper copied = objectMapper.copy();
        copied.registerModules(SecurityJackson2Modules.getModules(this.classLoader));
        return new GenericJackson2JsonRedisSerializer(copied);
    }
}
