package com.license.smapp;

import com.license.smapp.control.service.StorageService;
import com.license.smapp.entity.model.ApplicationState;
import com.license.smapp.entity.model.State;
import com.license.smapp.entity.model.User;
import com.license.smapp.entity.repository.ApplicationStateRepository;
import com.license.smapp.entity.repository.UserRepository;
import com.license.smapp.util.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@SpringBootApplication
public class SmappApplication implements CommandLineRunner{

	@Resource
	StorageService storageService;

	@Autowired
	private ApplicationStateRepository appStateRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(SmappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmappApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
		if (appStateRepository.findOne(Constants.STATE_ID) == null) {
			ApplicationState applicationState = new ApplicationState(Constants.STATE_ID, State.OPENED);
			appStateRepository.save(applicationState);
		}
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
