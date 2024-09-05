package dev.nathan.Employee;

import dev.nathan.Employee.EmployeeRegistration.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HewlettEmployeeApplication {
private static final Logger log= LoggerFactory.getLogger(HewlettEmployeeApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(HewlettEmployeeApplication.class, args);
	}
		@Bean
		CommandLineRunner runner(){
			return args ->{
				Employee employee=new Employee(1,"Mr","Nathan","male",25,"nathantolosa@gmail.com",25195626, LocalDateTime.now(),"Accounting");
			log.info("Employee:" + employee.full_name());
			};


	}



}
