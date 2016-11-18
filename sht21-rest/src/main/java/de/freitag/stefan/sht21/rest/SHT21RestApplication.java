package de.freitag.stefan.sht21.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

/**
 * SHT21 REST application.
 *
 * @author Stefan Freitag
 */

public final class SHT21RestApplication extends Application<SHT21RestConfiguration> {

    /**
     * Application entry point.
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        try {
            new SHT21RestApplication().run(args);
        } catch (final Exception exception) {
            getLogger().error(exception.getMessage(), exception);
            System.exit(-1);
        }
    }

    /**
     * Return the {@link Logger} for this class.
     *
     * @return The {@link Logger} for this class.
     */
    private static Logger getLogger() {
        return LogManager.getLogger(SHT21RestApplication.class.getCanonicalName());
    }

    @Override
    public String getName() {
        return "sht21";
    }

    @Override
    public void run(final SHT21RestConfiguration configuration,
                    final Environment environment) {
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration is null.");
        }
        if (environment == null) {
            throw new IllegalArgumentException("Environment is null.");
        }

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);


        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, environment.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(ALLOWED_METHODS_PARAM, "GET,PUT,POST,OPTIONS");
        filter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept");
        filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");


        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        final SHT21Controller resource = SHT21Controller.create(configuration);
        environment.jersey().register(resource);
    }
}

