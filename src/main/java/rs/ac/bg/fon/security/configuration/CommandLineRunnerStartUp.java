package rs.ac.bg.fon.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.entity.Role;
import rs.ac.bg.fon.entity.User;
import rs.ac.bg.fon.repository.RoleRepository;
import rs.ac.bg.fon.repository.UserRepository;
import rs.ac.bg.fon.service.UserService;

import java.util.ArrayList;
import java.util.Date;

@Component
public class CommandLineRunnerStartUp implements CommandLineRunner {

    private UserService userService;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private InitialAdminConfig initialAdminConfig;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.existsByEmail(initialAdminConfig.getEmail())) return;

        userService.saveRole(new Role(null, "ROLE_CLIENT"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));
        userService.saveUser(new User(null, initialAdminConfig.getName(),initialAdminConfig.getSurname(),
                initialAdminConfig.getEmail(),new Date(1999, 06, 23),initialAdminConfig.getUsername(), initialAdminConfig.getPassword(),
                new ArrayList<>()));

        userService.addRoleToUser("janko", "ROLE_CLIENT");
        userService.addRoleToUser("janko", "ROLE_ADMIN");


    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setInitialAdminConfig(InitialAdminConfig initialAdminConfig) {
        this.initialAdminConfig = initialAdminConfig;
    }
}
