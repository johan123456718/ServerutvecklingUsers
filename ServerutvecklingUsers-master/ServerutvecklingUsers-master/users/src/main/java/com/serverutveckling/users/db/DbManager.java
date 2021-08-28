package com.serverutveckling.users.db;

import com.serverutveckling.users.objects.Users;
import com.serverutveckling.users.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * Implements DbInterface to use a MySQL database
 */
@Component
public class DbManager implements DbInterface {

    @Autowired
    private userRepository userRepository;

    private static final String rootPath = "C://Users/empaf/OneDrive/Server/node/images/";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public DbManager(){

    }

    /**
     * Gets all usernames except callers
     * @param uuid
     * @return
     */
    @Override
    public ArrayList<Users> getUsernames(String uuid) {
        Iterable<Users> userList = userRepository.findAll();

        ArrayList<Users> usernameList = new ArrayList<>();
        usernameList.addAll((ArrayList) userList);

        for(int i = 0; i < usernameList.size(); i++){
            if(usernameList.get(i).getUUID().equals(uuid)){
                usernameList.remove(i);
            }else{
                usernameList.get(i).setPassword("");
            }
        }
        return usernameList;
    }

    @Override
    public String signIn(String username, String password){
        Optional <Users> user = userRepository.findByUsername(username);
        try {
            if (user.get().getUsername().equals(username) && passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get().getUUID();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        throw new BadCredentialsException("Invalid username/password supplied");
    }

    @Override
    public String registerUser(String username, String password) {
        UUID uuid = UUID.randomUUID();
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setUUID(uuid.toString());
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);

        return newUser.getUUID();
    }

    /**
     * Gets the encrypted password
     * @return
     */
    @Bean
    private PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
