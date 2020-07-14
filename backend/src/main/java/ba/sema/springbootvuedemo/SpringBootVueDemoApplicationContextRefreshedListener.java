package ba.sema.springbootvuedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class SpringBootVueDemoApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>
{
    private static final Logger logger = LoggerFactory.getLogger(SpringBootVueDemoApplicationContextRefreshedListener.class);

    private final Environment env;

    @Autowired
    public SpringBootVueDemoApplicationContextRefreshedListener(Environment env)
    {
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (env instanceof ConfigurableEnvironment)
        {
            MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();
            for (PropertySource ps : propertySources)
            {
                logger.info("Loaded property source from: {}", ps.getName());
            }
        }
    }
}
