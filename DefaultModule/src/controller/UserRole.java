package controller;

import model.Users;

/**
 * Created by Univetech Sarl on 05/02/2017.
 */
public class UserRole {

    private String role;
    private String username;
    private String user;
    private Users users;


    public UserRole(Users users) {
      this.users = users;
    }

    public String getUsername() {
        return this.users.getPseudo();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser() {
        return this.users.getName();
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return users.getRole();
    }
}
