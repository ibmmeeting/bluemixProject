package com.ibmMeeting;




import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.ibmMeeting.Job.SchedulerService;


@EnableScheduling
@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class IbmMeetingApplication extends SpringBootServletInitializer {
	
	// SpringScheduler Setting
	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Autowired 
	private SchedulerService scheduler;

	// warFile make
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IbmMeetingApplication.class);
	}
	
	// app run
	public static void main(String[] args) {
		SpringApplication.run(IbmMeetingApplication.class, args);
	}
	
//	@Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    }
// 
//    @Bean
//    public Filter characterEncodingFilter() {
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return characterEncodingFilter;
//    }
	
	@Bean
	public SimpleUrlHandlerMapping faviconHandlerMapping() {
	    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
	    mapping.setOrder(Integer.MIN_VALUE);
	    mapping.setUrlMap(Collections.singletonMap("favicon.ico",faviconRequestHandler()));
	    return mapping;
	}

	@Bean
	protected ResourceHttpRequestHandler faviconRequestHandler() {
	    ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
	    requestHandler.setLocations(Arrays
	            .<Resource> asList(new ClassPathResource("/")));
	    return requestHandler;
	}
	

}


