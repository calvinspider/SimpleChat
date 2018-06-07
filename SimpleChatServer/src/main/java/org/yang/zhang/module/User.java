package org.yang.zhang.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class User{

    private Integer id;

    private String name;

    private String password;

    private List<Role> roles;

    public List<Role> getRoles() {
        return new ArrayList<>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}