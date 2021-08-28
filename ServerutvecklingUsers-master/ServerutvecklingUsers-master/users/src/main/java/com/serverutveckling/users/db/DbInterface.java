package com.serverutveckling.users.db;


import com.serverutveckling.users.objects.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Interface declaring methods used for handling communication with a database
 */
@Component
public interface DbInterface {

    public ArrayList<Users> getUsernames(String uuid);

    String signIn(String username, String password);

    String registerUser(String username, String password);

}
