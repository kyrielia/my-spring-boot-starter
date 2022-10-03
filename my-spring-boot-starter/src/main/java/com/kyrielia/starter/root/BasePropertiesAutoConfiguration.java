package com.kyrielia.starter.root;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * This autoconfiguration pulls in a default set of properties from src/main/resources using Spring's property source
 * annotation. Services that use the Spring Boot starter would inherit these properties automatically. The services
 * are also able to override these properties in its application.yaml or application.properties file, as properties
 * packaged within a JAR take precedence over properties loaded using @PropertySource.
 *
 * See: https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html
 */
@AutoConfiguration
@PropertySource(value = "classpath:base-application.properties")
@SuppressWarnings("SpringFacetCodeInspection")
public class BasePropertiesAutoConfiguration {
}


