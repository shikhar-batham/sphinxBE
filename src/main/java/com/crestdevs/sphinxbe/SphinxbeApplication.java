package com.crestdevs.sphinxbe;

import com.crestdevs.sphinxbe.config.AppConstants;
import com.crestdevs.sphinxbe.entity.Role;
import com.crestdevs.sphinxbe.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SphinxbeApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(SphinxbeApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }


    @Override
    public void run(String... args) throws Exception {
        //table role
        try {
            Role role = new Role();

            role.setId(AppConstants.NORMAL_USER);
            role.setName("NORMAL_USER");

            Role role1 = new Role();
            role1.setId(AppConstants.ADMIN_USER);
            role1.setName("ADMIN_USER");

            List<Role> rolesList = List.of(role, role1);

            this.roleRepo.saveAll(rolesList);

            rolesList.forEach(r -> System.out.println(r.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
