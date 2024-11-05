package com.e201.kidswallet.user.entity;

import com.e201.kidswallet.user.enums.Gender;
import com.e201.kidswallet.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="`User`")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",nullable = false)
    private long userId;

    @Column(name = "user_name",nullable = false)
    private String userName;

    @Column(name = "user_password",nullable = false)
    private String userPassword;

    @Column(name = "user_birth",nullable = false)
    private LocalDate userBirth;

    @Column(name="user_email",nullable = false)
    private String userEmail;

    @Enumerated(value = EnumType.STRING)
    @Column(name="user_gender",nullable = false)
    private Gender userGender;

    @Column(name="user_realname",nullable = false)
    private String userRealname;

    @Column(name="user_point",nullable = false)
    private int userPoint=0;

    @Enumerated(value=EnumType.STRING)
    @Column(name="user_role")
    private Role userRole;

    @CreatedDate
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="delete_at",nullable = true)
    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "parent")
    private List<Relation> parentsRelations;  // 수정: parentId -> parent

    @OneToMany(mappedBy = "child")
    private List<Relation> childsRelations;  // 수정: childId -> chil


}
