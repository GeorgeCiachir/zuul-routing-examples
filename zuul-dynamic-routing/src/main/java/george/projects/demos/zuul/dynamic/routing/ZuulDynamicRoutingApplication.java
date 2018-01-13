package george.projects.demos.zuul.dynamic.routing;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@EnableZuulProxy
@SpringBootApplication
public class ZuulDynamicRoutingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ZuulDynamicRoutingApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ZuulDynamicRoutingApplication.class);
	}

	@Bean
	public HandlerMapping handlerMapping(ZuulController zuulController) {
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		((Map<String, Object>) mapping.getUrlMap()).put("/**", zuulController);
		mapping.setOrder(1);
		return mapping;
	}
}
