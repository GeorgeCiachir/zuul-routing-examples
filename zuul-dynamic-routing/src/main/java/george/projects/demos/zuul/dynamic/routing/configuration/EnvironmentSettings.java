package george.projects.demos.zuul.dynamic.routing.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import george.projects.demos.zuul.dynamic.routing.exceptions.InvalidUrlException;

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

	public URL getTargetedServiceUrl() {
		return createTargetedServiceUrl();
	}

	private URL createTargetedServiceUrl() {
		String dynamicUrl = environment.getProperty("dynamic.routing.variables.targetedServiceUrl");
		try {
			return new URL(dynamicUrl);
		} catch (MalformedURLException ex) {
			String message = String.format("Provided url [%s] does not have a valid format. Please review the property", dynamicUrl);
			LOG.error(message);
			throw new InvalidUrlException(message, ex);
		}
	}
}
