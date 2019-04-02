package top.youthfighter.dailyreport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.youthfighter.dailyreport.mapper")
@SpringBootApplication
public class DailyreportApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyreportApplication.class, args);
	}

}
