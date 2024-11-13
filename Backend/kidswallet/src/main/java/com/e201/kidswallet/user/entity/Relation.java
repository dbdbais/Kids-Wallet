package com.e201.kidswallet.user.entity;

import com.e201.kidswallet.mission.entity.Beg;
import com.e201.kidswallet.togetherrun.entity.TogetherRun;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
//연관관계의 주인
@Table(name = "relation", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"parent_idparent_id", "children_id"})
})
public class Relation {
    @Id
    @Column(name="relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    /*
        Relation 엔티티에서 parent 필드와 @JoinColumn 어노테이션을 통해 parent_id 컬럼이 생성되며,
        이 컬럼은 user 테이블의 user_id를 참조하는 외래 키가 됩니다.
     */

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "user_id")
    private User parent;

    @ManyToOne
    @JoinColumn(name = "children_id", referencedColumnName = "user_id")
    private User child;

    @OneToMany()
    private List<TogetherRun> togetherRunList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "relation",cascade = CascadeType.ALL)
    private List<Beg> Begs;

    @Override
    public String toString() {
        return "Relation{" +
                "relationId=" + relationId +
                '}';
    }

}
