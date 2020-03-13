package com.alten.teamservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@XmlRootElement(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "top_employee", nullable = false)
    private boolean topEmployee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_roles", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Task> tasks;


    @ManyToMany(mappedBy = "employees")
    private List<Team> teams;

    @Column(name = "version", nullable = false)
    private int version;

    public Employee() {
        roles = new ArrayList<Role>();
        tasks = new ArrayList<Task>();
        teams = new ArrayList<Team>();
    }

    public Employee(String userName, String password, String firstName, String lastName, String email,
                    boolean topEmployee) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.topEmployee = topEmployee;
        roles = new ArrayList<Role>();
        tasks = new ArrayList<Task>();
        teams = new ArrayList<Team>();
    }

    public Employee(String userName, String password, String firstName, String lastName, String email,
                    boolean topEmployee, List<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.topEmployee = topEmployee;
        this.roles = roles;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTopEmployee() {
        return topEmployee;
    }

    public void setTopEmployee(boolean topEmployee) {
        this.topEmployee = topEmployee;
    }

    public List<Role> getRoles() {
        if (roles == null)
            roles = new ArrayList<Role>();
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", topEmployee=" + topEmployee + ", roles=" + roles
                + ", version=" + version + "]";
    }

}
