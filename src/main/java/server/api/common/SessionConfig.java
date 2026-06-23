package server.api.common;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson.SecurityJacksonModules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import server.api.sessions.SessionUserData;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
@EnableRedisIndexedHttpSession(maxInactiveIntervalInSeconds = 2592000)
public class SessionConfig implements BeanClassLoaderAware {
  private ClassLoader loader;

  @Bean
  public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    return new JacksonJsonRedisSerializer<>(objectMapper(), Object.class);
  }

  private JsonMapper objectMapper() {

    BasicPolymorphicTypeValidator.Builder ptv =
        BasicPolymorphicTypeValidator.builder()
            .allowIfSubType(SessionUserData.class)
            .allowIfSubType(Number.class);

    return JsonMapper.builder()
        .addModules(SecurityJacksonModules.getModules(this.loader, ptv))
        .build();
  }

  @Override
  public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
    this.loader = classLoader;
  }
}
