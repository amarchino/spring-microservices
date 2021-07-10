package guru.springframework.msscbreweryclient.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties("sfg.http")
@Component
@Data
public class HttpClientConfig {

	private Integer maxConnections = 1;
	private Integer maxPerRoute;
	
	private Integer connectionTimeout;
	private Integer socketTimeout;
}
