package org.smartfiles.configuration;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;

@Configuration
public class HysterixFilter implements GatewayFilterFactory<RequestEntity<String>> {

	@Override
	public GatewayFilter apply(RequestEntity<String> config) {
		// TODO Auto-generated method stub
		return null;
	}

}
