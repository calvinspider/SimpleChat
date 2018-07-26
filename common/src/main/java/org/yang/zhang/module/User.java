package org.yang.zhang.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickName;
    private String personWord;
    private String sex;
    private String age;
    private String birthday;
    private String zodiac;
    private String constellation;
    private String bloodType;
    private String homeTown;
    private String locate;
    private String phone;
    private String page;
    private String email;
    private String iconUrl;
    private String password;
}