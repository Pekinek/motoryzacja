package backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import backend.model.User;

@Configuration
public class MyConfiguration extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(User.class);
	}
}