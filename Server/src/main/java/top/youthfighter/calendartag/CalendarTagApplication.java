package top.youthfighter.calendartag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.youthfighter.calendartag.mapper")
@SpringBootApplication
public class CalendarTagApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarTagApplication.class, args);
	}

}
