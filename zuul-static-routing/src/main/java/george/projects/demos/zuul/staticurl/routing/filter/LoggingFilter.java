package george.projects.demos.zuul.staticurl.routing.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class LoggingFilter extends ZuulFilter {

	private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();

		LOG.info("Request URL: {}", context.getRequest().getRequestURI());

		return null;
	}
}
