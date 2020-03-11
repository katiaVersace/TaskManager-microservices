package com.alten.employeeservice;

import com.alten.employeeservice.dto.TaskDto;
import com.alten.employeeservice.model.Task;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class EmployeeServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

//    @PostConstruct
//    private void initDb() {
//
//        EmployeeDto ciccio = new EmployeeDto();
//        ciccio.setUserName("ciccio");
//        ciccio.setPassword("123");
//        ciccio.setFirstName("ciccio");
//        ciccio.setLastName("pasticcio");
//        ciccio.setEmail("ciccio@alten.it");
//        employeeService.save(ciccio);
//
//
//    }
@Bean
public Docket newsApi() {

    return new Docket(DocumentationType.SWAGGER_2).groupName("Employee Service").apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
}

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Employee Service API with Swagger").description("Employee Service API with Swagger")
                .contact(new Contact("Katia Versace", "", "caterina.versace@alten.it"))
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("2.0").build();
    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Task, TaskDto> typeMapToDto = modelMapper.createTypeMap(Task.class, TaskDto.class);

        typeMapToDto.addMappings(mapper -> {
            mapper.map(src -> src.getEmployee().getId(), TaskDto::setEmployeeId);

        });

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                LocalDate now = LocalDate.now();
                return now;
            }
        };

        Converter<String, LocalDate> toDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(source, format);
                return localDate;
            }
        };


        modelMapper.createTypeMap(String.class, LocalDate.class);

        modelMapper.addConverter(toDate);

        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        return modelMapper;
    }
}
