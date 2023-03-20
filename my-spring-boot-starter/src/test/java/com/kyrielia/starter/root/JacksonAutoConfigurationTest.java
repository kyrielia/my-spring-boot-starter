package com.kyrielia.starter.root;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class JacksonAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(
          JacksonAutoConfiguration.class
      ));

  @Test
  void shouldAddObjectMapperBeanByDefault() {
    this.contextRunner.run(context -> {
      var objectMapper = context.getBean("objectMapper");
      assertThat(objectMapper).isInstanceOf(ObjectMapper.class);
    });
  }

  @Test
  void shouldFavourExistingObjectMapperOverAutoconfiguredObjectMapper() {
    this.contextRunner.withBean("testObjectMapper", ObjectMapper.class, new ObjectMapper())
        .run(context -> {
          assertThat(context).hasSingleBean(ObjectMapper.class);
          assertThat(context.containsLocalBean("objectMapper")).isFalse();

          var objectMapper = context.getBean("testObjectMapper");
          assertThat(objectMapper).isInstanceOf(ObjectMapper.class);
        });
  }
}