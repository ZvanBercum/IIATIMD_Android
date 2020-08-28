package com.example.android.Resources;

/**
 * @class User
 * This is the class for the user
 */
public class User {
    //Name of the user
    private String name;
    //If the user is logged in or not
    private Boolean logged;

    /**
     * @class Constructor
     * @param name String
     */
    public User(String name){
        this.name = name;
        this.logged = true;
    }

    /**
     * @func getName
     * @return name
     * Gets the user name
     */
    public String getName(){return name;}

    /**
     * @func setName
     * @param name String
     * This function sets the name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @func getLogged
     * @return logged Boolean
     * This function returns if the user is logged in
     */
    public Boolean getLogged(){return logged;}

    /**
     * @func setLogged
     * @param logged Boolean
     * This function sets the logged variable
     */
    public void setLogged(Boolean logged){this.logged = logged;}
}
