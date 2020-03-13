package com.alten.teamservice.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@XmlRootElement(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expected_start_time", columnDefinition = "DATE")
    private LocalDate expectedStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "real_start_time", columnDefinition = "DATE")
    private LocalDate realStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expected_end_time", columnDefinition = "DATE")
    private LocalDate expectedEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "real_end_time", columnDefinition = "DATE")
    private LocalDate realEndTime;

    @Column(name = "version", nullable = false)
    private int version;

    public Task(String description, LocalDate expectedStartTime, LocalDate realStartTime, LocalDate expected_end_time,
                LocalDate realEndTime) {
        super();
        this.description = description;
        this.expectedStartTime = expectedStartTime;
        this.realStartTime = realStartTime;
        this.expectedEndTime = expected_end_time;
        this.realEndTime = realEndTime;
        this.version = 0;
    }

    public Task() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(LocalDate expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public LocalDate getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(LocalDate realStartTime) {
        this.realStartTime = realStartTime;
    }

    public LocalDate getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(LocalDate expected_end_time) {
        this.expectedEndTime = expected_end_time;
    }

    public LocalDate getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(LocalDate realEndTime) {
        this.realEndTime = realEndTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", employee=" + employee + ", expectedStartTime="
                + expectedStartTime + ", realStartTime=" + realStartTime + ", expectedEndTime=" + expectedEndTime
                + ", realEndTime=" + realEndTime + ", version=" + version + "]";
    }

}
