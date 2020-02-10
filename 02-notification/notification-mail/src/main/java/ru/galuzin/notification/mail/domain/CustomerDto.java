package ru.galuzin.notification.mail.domain;

public class CustomerDto {

    public Long id;

    public String name;

    public String email;

    public String phone;

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
