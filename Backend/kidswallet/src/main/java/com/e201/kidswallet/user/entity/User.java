package com.e201.kidswallet.user.entity;

import com.e201.kidswallet.account.entity.Account;
import com.e201.kidswallet.user.enums.Gender;
import com.e201.kidswallet.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="User")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",nullable = false)
    private long userId;

    @Builder.Default
    @Column(name="representative_account_id", nullable = true)
    private String representAccountId = null;

    @Builder.Default
    @Column(name="isCard")
    private boolean hasCard = false;

    @Column(name = "user_name",nullable = false)
    private String userName;

    @Column(name = "user_password",nullable = false)
    private String userPassword;

    @Column(name = "user_birth",nullable = false)
    private LocalDate userBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(name="user_gender",nullable = false)
    private Gender userGender;

    @Column(name="user_real_name",nullable = false)
    private String userRealName;

    @Builder.Default
    @Column(name="user_money",nullable = false)
    private int userMoney=0;

    @Enumerated(value=EnumType.STRING)
    @Column(name="user_role")
    private Role userRole;

    @CreatedDate
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="delete_at",nullable = true)
    private LocalDateTime deleteAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    public void makeCard(){
        this.hasCard = true;
    }

    public void depositMoney(int money){
        this.userMoney += money;
    }
    public void withdrawMoney(int money){
        this.userMoney -= money;
    }
    public void initAccount(){
        this.userMoney = 0;
    }

    public void setRepresentAccountId(String registerId){
        this.representAccountId = registerId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", representAccountId='" + representAccountId + '\'' +
                ", hasCard=" + hasCard +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userBirth=" + userBirth +
                ", userGender=" + userGender +
                ", userRealName='" + userRealName + '\'' +
                ", userMoney=" + userMoney +
                ", userRole=" + userRole +
                ", createdAt=" + createdAt +
                ", deleteAt=" + deleteAt +
                ", accounts=" + accounts +
                '}';
    }
}
