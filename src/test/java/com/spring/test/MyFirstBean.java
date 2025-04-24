package com.spring.test;

/**
 * 第一个bean
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class MyFirstBean {
    private String name;
    private String nickname;
    private String city;
    private Integer age;

    public MyFirstBean() {
    }

    public MyFirstBean(String city, Integer age) {
        this.city = city;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    public void print() {
        System.out.println("this is first bean");
        System.out.println("name: " + name + ", nickName: " + nickname + ", city: " + city + ", age: " + age);
    }

}
