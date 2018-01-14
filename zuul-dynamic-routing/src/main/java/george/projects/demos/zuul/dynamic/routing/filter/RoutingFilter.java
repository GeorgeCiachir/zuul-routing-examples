package george.projects.demos.zuul.dynamic.routing.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import george.projects.demos.zuul.dynamic.routing.configuration.EnvironmentSettings;

/**
 * Used for determining appropriate request routing destination, based on a condition
 * <p>
 * This is not an actual "router" in the context of the Zuul system, but more of a decoration filter,
 * so it seems appropriate to set it as a "pre" filter, because in the Zuul context,
 * the "route" filter performs the actual routing of the request
 * <p>
 * It can be rewritten as a "route" filter, with the condition that the {@link #filterOrder} is lower than that of
 * the other Zuul "route" filters.
 */
@Component
public class RoutingFilter extends ZuulFilter {

	private static final String SERVICE_PARAM = "service";

	@Resource
	private EnvironmentSettings settings;

	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * The filter order must activate after the {@link PreDecorationFilter}
	 */
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

		requestContext.setRouteHost(determineRouteHost(requestContext));
		requestContext.remove(FORWARD_TO_KEY);
		requestContext.put(REQUEST_URI_KEY, requestContext.getRequest().getRequestURI());

		return null;
	}

	private URL determineRouteHost(RequestContext requestContext) {
		List<String> serviceParams = requestContext.getRequestQueryParams().get(SERVICE_PARAM);

		if (serviceParams == null) {
			return settings.getStaticUrl();
		}

		return settings.getServiceUrl(serviceParams.get(0));
	}
}
