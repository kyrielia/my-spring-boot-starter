package com.kyrielia.starter.root;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.google.common.base.Preconditions;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@AutoConfiguration
public class OpenApiAutoConfiguration {

  @Bean
  public OpenAPI openApi(
      @Value("${app.info.title:}") String title,
      @Value("${app.info.description:}") String description,
      @Value("${app.info.version:}") String version
  ) {
    Preconditions.checkArgument(isNotEmpty(title), "Please add a value for 'app.info.title' to your application.yaml/application.properties");
    Preconditions.checkArgument(isNotEmpty(description), "Please add a value for 'app.info.description' to your application.yaml/application.properties");
    Preconditions.checkArgument(isNotEmpty(version), "Please add a value for 'app.info.version' to your application.yaml/application.properties");

    Info info = new Info()
        .title(title)
        .description(description)
        .version(version);

    return new OpenAPI()
        .info(info);
  }
}
