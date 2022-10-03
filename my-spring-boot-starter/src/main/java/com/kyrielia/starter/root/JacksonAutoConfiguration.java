package com.kyrielia.starter.root;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * An autoconfiguration that will create a Jackson ObjectMapper if the Spring application context currently does not
 * have an ObjectMapper bean.
 *
 * This pattern is useful to give applications using the starter out-of-the-box functionality should they need it, but
 * also allows this functionality to be overridden when required.
 */
@AutoConfiguration
@ConditionalOnMissingBean(ObjectMapper.class)
public class JacksonAutoConfiguration {

  public static ObjectMapper anObjectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(NON_NULL)
        .build()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .findAndRegisterModules()
        .registerModule(new JavaTimeModule());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return anObjectMapper();
  }
}
