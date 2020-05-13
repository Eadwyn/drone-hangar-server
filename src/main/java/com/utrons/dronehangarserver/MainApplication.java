package com.utrons.dronehangarserver;

import com.utrons.dronehangarserver.comm.TCPClientHelper;
import com.utrons.dronehangarserver.thread.CommReceivedHandlerThread;
import com.utrons.dronehangarserver.thread.CommReceiverThread;
import com.utrons.dronehangarserver.thread.CommSenderThread;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(MainApplication.class)
				.beanNameGenerator(new CustomBeanNameGenerator())
				.run(args);
//		SpringApplication.run(MainApplication.class, args);
		init();
	}

	public static class CustomBeanNameGenerator implements BeanNameGenerator {
		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}

	private static void init() {
		CommReceivedHandlerThread.getInstance().start();
		CommReceiverThread.getInstance().start();
		CommSenderThread.getInstance().start();
		TCPClientHelper.connect();
	}
}
