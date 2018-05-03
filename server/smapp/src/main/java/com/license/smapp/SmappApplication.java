package com.license.smapp;

import com.license.smapp.service.StorageService;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class SmappApplication implements CommandLineRunner{

	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SmappApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
//	//Tomcat large file upload connection reset
//	//http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
//	@Bean
//	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
//
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//
//		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
//			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
//				//-1 means unlimited
//				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
//			}
//		});
//
//		return tomcat;
//	}
}
