package hg.community;

import hg.community.config.AopConfig;
import hg.community.trace.logtrace.LogTrace;
import hg.community.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableJpaAuditing
@Import(AopConfig.class)
@EnableRedisHttpSession
@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}
	@Bean
	public LogTrace logTrace() {
		return new ThreadLocalLogTrace();
	}

}
