package george.projects.demos.zuul.dynamic.routing.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import george.projects.demos.zuul.dynamic.routing.configuration.EnvironmentSettings;

@Component
public class RoutingFilter extends ZuulFilter {

	@Resource
	private EnvironmentSettings settings;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER + 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();

		requestContext.setRouteHost(settings.getTargetedServiceUrl());
		requestContext.remove(FORWARD_TO_KEY);
		requestContext.put(REQUEST_URI_KEY, requestContext.getRequest().getRequestURI());

		return null;
	}
}
