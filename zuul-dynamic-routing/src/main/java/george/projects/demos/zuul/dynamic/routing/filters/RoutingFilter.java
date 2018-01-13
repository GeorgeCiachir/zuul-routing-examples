package george.projects.demos.zuul.dynamic.routing.filters;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class RoutingFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 6;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();

		try {
			requestContext.setRouteHost(new URL("https://spring.io/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		requestContext.remove(FORWARD_TO_KEY);
		requestContext.put(REQUEST_URI_KEY, "/projects");
		return null;
	}
}
