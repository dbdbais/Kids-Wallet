package com.e201.kidswallet.user.entity;

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
public class Relation {
    @Id
    @Column(name="relation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long relationId;

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

}
