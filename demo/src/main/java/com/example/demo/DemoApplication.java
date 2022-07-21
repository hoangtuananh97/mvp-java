package com.example.demo;

import com.example.demo.model.MemberTypes;
import com.example.demo.repository.MemberTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public CommandLineRunner mapDemo(MemberTypeRepository memberTypeRepository){
        return args -> {
            memberTypeRepository.save(new MemberTypes("SLIVER",Utils.toLong(15000),Utils.toLong(30000)));
            memberTypeRepository.save(new MemberTypes("GOLD",Utils.toLong(30001),Utils.toLong(50000)));
            MemberTypes memberTypes = new MemberTypes();
            memberTypes.setTypeClassify("PLATINUM");
            memberTypes.setFromSalary(Utils.toLong(50001));
            memberTypes.setLimitSalary(false);
            memberTypeRepository.save(memberTypes);
        };
    }
}
