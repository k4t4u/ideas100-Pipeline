package pl.k4t.ideas100;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ideas100")
@Data
public class Ideas100Configuration {

	private String name;

	@Value("${paging.pageSize:2}")
	private int pagingPageSize;
}
