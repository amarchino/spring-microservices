package guru.springframework.brewery.events;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.ManagedWireMockServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;

@ExtendWith(WireMockExtension.class)
class BeerOrderStatusChangeEventListenerTest {
	
	@Managed
	WireMockServer wireMockServer = ManagedWireMockServer.with(WireMockConfiguration.wireMockConfig().dynamicPort());

	BeerOrderStatusChangeEventListener listener;

	@BeforeEach
	void setUp() throws Exception {
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		listener = new BeerOrderStatusChangeEventListener(restTemplateBuilder);
	}

	@Test
	void listen() {
		wireMockServer.stubFor(WireMock.post("/update").willReturn(WireMock.ok()));
		
		BeerOrder beerOrder = BeerOrder.builder()
				.orderStatus(OrderStatusEnum.READY)
				.orderStatusCallbackUrl("http://localhost:" + wireMockServer.port() + "/update")
				.createdDate(Timestamp.valueOf(LocalDateTime.now()))
				.build();
		BeerOrderStatusChangeEvent event = new BeerOrderStatusChangeEvent(beerOrder, OrderStatusEnum.NEW);
		listener.listen(event);
		
		WireMock.verify(1, WireMock.postRequestedFor(WireMock.urlEqualTo("/update")));
	}

}
