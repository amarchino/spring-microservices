package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {
	
	private final HttpClientConfig httpClientConfig;
	
	@Override
	public void customize(RestTemplate restTemplate) {
		restTemplate.setRequestFactory(clientHttpRequestFactory());
	}
	
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(httpClientConfig.getMaxConnections());
		connectionManager.setDefaultMaxPerRoute(httpClientConfig.getMaxPerRoute());
		
		RequestConfig requestConfig = RequestConfig
			.custom()
			.setConnectionRequestTimeout(httpClientConfig.getConnectionTimeout())
			.setSocketTimeout(httpClientConfig.getSocketTimeout())
			.build();
		
		CloseableHttpClient httpClient = HttpClients
			.custom()
			.setConnectionManager(connectionManager)
			.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
			.setDefaultRequestConfig(requestConfig)
			.build();
		
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

}
