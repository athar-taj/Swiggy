package cln.swiggy.swiggyRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SwiggyRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyRegistryApplication.class, args);
	}

}
