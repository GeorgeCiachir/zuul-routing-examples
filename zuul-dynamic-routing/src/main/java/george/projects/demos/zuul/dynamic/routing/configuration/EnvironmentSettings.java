package george.projects.demos.zuul.dynamic.routing.configuration;

import static com.google.common.base.Preconditions.checkArgument;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import george.projects.demos.zuul.dynamic.routing.exception.InvalidUrlFormatException;

@Component
@ConfigurationProperties(prefix = "dynamic.routing.variables")
public class EnvironmentSettings {

	private static final Logger LOG = LoggerFactory.getLogger(EnvironmentSettings.class);

	private Environment environment;
	private URL staticUrl;

	public EnvironmentSettings(Environment environment) {
		this.environment = environment;
	}

	public URL getStaticUrl() {
		return staticUrl;
	}

	public void setStaticUrl(URL staticUrl) {
		this.staticUrl = staticUrl;
	}

	public URL getServiceUrl(String serviceName) {
		return createTargetedServiceUrl(serviceName);
	}

	private URL createTargetedServiceUrl(String serviceName) {
		checkArgument(StringUtils.isNotEmpty(serviceName), "Service name must be provided as a request parameter");

		String dynamicUrl = environment.getProperty("dynamic.routing.variables." + serviceName);
		try {
			return new URL(dynamicUrl);
		} catch (MalformedURLException ex) {
			String message = String.format("Provided url [%s] does not have a valid format. Please review the property", dynamicUrl);
			LOG.error(message);
			throw new InvalidUrlFormatException(message, ex);
		}
	}
}
