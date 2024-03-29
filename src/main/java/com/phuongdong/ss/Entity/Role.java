package com.phuongdong.ss.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Role")
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

//    public void removeAllUserFromRole(){
//        if(this.getUsers() != null){
//            List<User> userInRole = this.getUsers().stream().toList();
//            userInRole.forEach(this::removeUserFromRole);
//        }
//    }

//    public void removeUserFromRole(User user) {
//        user.getRoles().remove(this);
//        this.getUsers().remove(user);
//    }
//
//    public void assignUserToRole(User user){
//        user.getRoles().add(this);
//        this.getUsers().add(user);
//    }

}
