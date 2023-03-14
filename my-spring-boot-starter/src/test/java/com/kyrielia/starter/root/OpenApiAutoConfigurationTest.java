package com.kyrielia.starter.root;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import org.junit.jupiter.api.Test;

import io.swagger.v3.oas.models.OpenAPI;

class OpenApiAutoConfigurationTest {

  private static final String APP_TITLE = "MyApp";
  private static final String APP_DESCRIPTION = "A description of my app that appears on the OpenAPI page";
  private static final String APP_VERSION = "1.0.0";

  @Test
  void shouldGenerateAnOpenApiBeanIfAllExpectedPropertiesAreProvided() {
    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withPropertyValues(
            "app.info.title="+ APP_TITLE,
            "app.info.description=" + APP_DESCRIPTION,
            "app.info.version=" + APP_VERSION
        )
        .withConfiguration(AutoConfigurations.of(
            OpenApiAutoConfiguration.class
        ));

    contextRunner.run(context -> {
      var openApi = context.getBean(OpenAPI.class);
      var info = openApi.getInfo();

      assertThat(info.getTitle()).isEqualTo(APP_TITLE);
      assertThat(info.getDescription()).isEqualTo(APP_DESCRIPTION);
      assertThat(info.getVersion()).isEqualTo(APP_VERSION);
    });
  }

  @Test
  void shouldPreventApplicationStartUpIfNoTitleIsProvided() {
    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withPropertyValues(
            "app.info.description=" + APP_DESCRIPTION,
            "app.info.version=" + APP_VERSION
        )
        .withConfiguration(AutoConfigurations.of(
            OpenApiAutoConfiguration.class
        ));

    contextRunner.run(context -> assertThat(context.getStartupFailure()).isNotNull());
  }

  @Test
  void shouldPreventApplicationStartUpIfNoDescriptionIsProvided() {
    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withPropertyValues(
            "app.info.title="+ APP_TITLE,
            "app.info.version=" + APP_VERSION
        )
        .withConfiguration(AutoConfigurations.of(
            OpenApiAutoConfiguration.class
        ));

    contextRunner.run(context -> assertThat(context.getStartupFailure()).isNotNull());
  }

  @Test
  void shouldPreventApplicationStartUpIfNoVersionIsProvided() {
    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withPropertyValues(
            "app.info.title="+ APP_TITLE,
            "app.info.description=" + APP_DESCRIPTION
        )
        .withConfiguration(AutoConfigurations.of(
            OpenApiAutoConfiguration.class
        ));

    contextRunner.run(context -> assertThat(context.getStartupFailure()).isNotNull());
  }
}
