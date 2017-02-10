package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MySlippTerminalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySlippTerminalApplication.class, args);
	}
}
