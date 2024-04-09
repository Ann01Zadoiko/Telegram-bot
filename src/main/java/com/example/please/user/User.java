package com.example.please.user;


import com.example.please.notification.Notification;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "at_work")
    private byte atWork;

    @Column(name = "time_of_coming")
    private LocalTime timeComing;

    @Column(name = "room")
    private Integer room;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", atWork=" + atWork +
                ", timeComing=" + timeComing +
                ", room=" + room +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + statusEnum +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPassword() {
        return this.password;
    }

    public byte getAtWork() {
        return this.atWork;
    }

    public LocalTime getTimeComing() {
        return this.timeComing;
    }

    public Integer getRoom() {
        return this.room;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public StatusEnum getStatusEnum() {
        return this.statusEnum;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAtWork(byte atWork) {
        this.atWork = atWork;
    }

    public void setTimeComing(LocalTime timeComing) {
        this.timeComing = timeComing;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
