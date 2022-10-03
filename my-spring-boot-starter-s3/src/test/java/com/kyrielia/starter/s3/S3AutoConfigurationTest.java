package com.kyrielia.starter.s3;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.net.URI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import software.amazon.awssdk.core.client.config.SdkClientConfiguration;
import software.amazon.awssdk.core.client.config.SdkClientOption;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;

class S3AutoConfigurationTest {

  private static final String REGION = Region.EU_WEST_2.id();
  private static final String MOCK_URL = "http://localhost:4566";

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withPropertyValues("aws.region=" + REGION)
      .withConfiguration(AutoConfigurations.of(
          S3AutoConfiguration.class
      ));

  @Test
  void contextShouldHaveS3Client_noMock() {
    this.contextRunner.run(context -> {
      assertThat(context).hasSingleBean(S3Client.class);

      var s3Client = context.getBean(S3Client.class);
      assertThat(getRegion(s3Client)).isEqualTo(REGION);
      assertThat(getEndpointUrl(s3Client)).isEqualTo(URI.create("https://s3.eu-west-2.amazonaws.com"));
    });
  }

  @Test
  void contextShouldHaveS3Client_mock() {
    this.contextRunner
        .withPropertyValues(
            "aws.mock.enabled=true",
            "aws.mock.url=" + MOCK_URL
        )
        .run(context -> {
          assertThat(context).hasSingleBean(S3Client.class);

          var s3Client = context.getBean(S3Client.class);
          assertThat(getRegion(s3Client)).isEqualTo(REGION);
          assertThat(getEndpointUrl(s3Client)).isEqualTo(URI.create(MOCK_URL));
        });
  }

  @SneakyThrows
  private String getRegion(S3Client s3Client) {
    Field regionField = S3Utilities.class.getDeclaredField("region");
    regionField.setAccessible(true);
    var region = (Region) regionField.get(s3Client.utilities());
    return region.id();
  }

  @SneakyThrows
  private URI getEndpointUrl(S3Client s3Client) {
    var defaultS3ClientClass = Class.forName("software.amazon.awssdk.services.s3.DefaultS3Client");
    Field clientConfigurationField = defaultS3ClientClass.getDeclaredField("clientConfiguration");
    clientConfigurationField.setAccessible(true);
    var config = (SdkClientConfiguration) clientConfigurationField.get(s3Client);
    return config.option(SdkClientOption.ENDPOINT);
  }
}