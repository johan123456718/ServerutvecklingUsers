package com.serverutveckling.users.controller;

import com.serverutveckling.users.db.DbInterface;
import com.serverutveckling.users.db.DbManager;
import com.serverutveckling.users.objects.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


/**
 * Controller handling REST calls
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/demo", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, produces = { "application/json; charset=utf-8" })
public class RestController {

    @Autowired
    DbInterface DbManager = new DbManager();

    @GetMapping(path="/usernames")
    public @ResponseBody
    ArrayList<Users> getUsernames(@RequestParam String uuid){
        return DbManager.getUsernames(uuid);
    }

    @PostMapping(path = "/signIn")
    public @ResponseBody ResponseEntity signIn(@RequestParam String username, @RequestParam String password){
        try{
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("uuid", DbManager.signIn(username, password));
            return ok(model);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping(path = "/register")
    public @ResponseBody ResponseEntity register(@RequestParam String username, @RequestParam String password){
        try{
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("uuid", DbManager.registerUser(username, password));
            return ok(model);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}
