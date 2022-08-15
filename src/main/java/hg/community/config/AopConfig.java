package hg.community.config;

import hg.community.aop.aspect.LogTraceAspect;
import hg.community.aop.aspect.AuthCheckAspect;
import hg.community.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public AuthCheckAspect authCheckAspect() {
        return new AuthCheckAspect();
    }
}
