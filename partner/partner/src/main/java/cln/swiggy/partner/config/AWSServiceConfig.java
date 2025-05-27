package cln.swiggy.partner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.aws.service.s3bucket.service","com.aws.service.sns.service" })
public class AWSServiceConfig {
}
