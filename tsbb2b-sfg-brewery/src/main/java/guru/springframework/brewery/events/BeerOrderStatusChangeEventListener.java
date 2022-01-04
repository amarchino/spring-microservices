/*
 *  Copyright 2019 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package guru.springframework.brewery.events;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.brewery.web.mappers.DateMapper;
import guru.springframework.brewery.web.model.OrderStatusUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BeerOrderStatusChangeEventListener {
	
	RestTemplate restTemplate;
	DateMapper dateMapper = new DateMapper();
	
	public BeerOrderStatusChangeEventListener(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

    @Async
    @EventListener
    public void listen(BeerOrderStatusChangeEvent event){
    	log.info("I got an order status change event: " + event);
    	OrderStatusUpdate update = OrderStatusUpdate.builder()
    			.id(event.getSource().getId())
    			.orderId(event.getSource().getId())
    			.version(event.getSource().getVersion() != null ? event.getSource().getVersion().intValue() : null)
    			.createdDate(dateMapper.asOffsetDateTime(event.getSource().getCreatedDate()))
    			.lastModifiedDate(dateMapper.asOffsetDateTime(event.getSource().getLastModifiedDate()))
    			.orderStatus(event.getSource().getOrderStatus().toString())
    			.customerRef(event.getSource().getCustomerRef())
    			.build();
    	log.debug("Posting to callback url");
    	try {
    		restTemplate.postForObject(event.getSource().getOrderStatusCallbackUrl(), update, String.class);
    	} catch(Throwable t) {
    		log.error("Error performing callback for order: " + event.getSource().getId(), t);
    	}
    }
}
