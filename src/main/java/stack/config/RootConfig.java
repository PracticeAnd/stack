package stack.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

/**
 * Created by Оля on 05.07.2016.
 */


@Configuration
@ComponentScan(basePackages = {"stack"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, value = RootConfig.WebPackage.class)
        })
public class RootConfig {

    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("stack\\.web"));
        }
    }
}
