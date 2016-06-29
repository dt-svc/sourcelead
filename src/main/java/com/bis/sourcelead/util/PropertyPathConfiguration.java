package com.bis.sourcelead.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/** This is the class to supply PropertySourcesPlaceholderConfigurer Bean for to hold properties from properties file
 * 
 * @author: Naresh Bolisetty
 * @date: 01-June-2016
 */
@Configuration
@ComponentScan
public class PropertyPathConfiguration {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}