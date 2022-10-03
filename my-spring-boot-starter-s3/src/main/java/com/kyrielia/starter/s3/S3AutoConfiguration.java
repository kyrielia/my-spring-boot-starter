package com.kyrielia.starter.s3;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Autoconfiguration to create an Amazon S3 client for applications that use the starter.
 *
 * If mocking is enabled, the client will be configured to point at the URL provided by the mockUrl property. This is
 * useful for testing with an AWS mock client such as Localstack.
 */
@AutoConfiguration
public class S3AutoConfiguration {

  @Bean
  public S3Client s3Client(
      @Value("${aws.region:eu-west-1}") String region,
      @Value("${aws.mock.enabled:false}") Boolean mockEnabled,
      @Value("${aws.mock.url:http://localhost:4510}") String mockUrl
  ) {
    var s3Client = S3Client.builder().region(Region.of(region));

    if (mockEnabled) {
      s3Client.endpointOverride(URI.create(mockUrl));
    }

    return s3Client.build();
  }
}
