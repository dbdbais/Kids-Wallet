package com.e201.kidswallet.donation.entity;

import com.e201.kidswallet.donation.enums.DonationStatus;
import com.e201.kidswallet.user.entity.User;
import com.e201.kidswallet.user.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name="donation")
public class Donation{
    @Id
    @Column(name="donation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="donation_name")
    private String name;

    @Column(name = "donation_organizer")
    private String organName;

    @Column(name ="target_amount")
    private int target;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "donation_description")
    private String description;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(name="status",nullable = false)
    private DonationStatus donationStatus = DonationStatus.proceed;

    @CreatedDate
    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="delete_at",nullable = true)
    private LocalDateTime deleteAt;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "donation_user", joinColumns = @JoinColumn(name = "donation_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "amount")
    private Map<User, Integer> userMap = new HashMap<>();

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", organName='" + organName + '\'' +
                ", target=" + target +
                ", targetDate=" + targetDate +
                ", description='" + description + '\'' +
                ", donationStatus=" + donationStatus +
                ", createdAt=" + createdAt +
                ", deleteAt=" + deleteAt +
                ", userMap=" + userMap +
                '}';
    }
}
