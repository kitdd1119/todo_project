package com.example.my.model.user.entity;

import com.example.my.model.todo.entity.TodoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`USER`") // USER는 예약어라서 백틱으로 감싸줘야한다.
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idx", callSuper = false)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false, unique = true)
    private Long idx;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_date" , nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date") // 삭제가 되었는지 여부를 판단
    private LocalDateTime deleteDate;

    // mappedBy와 join할 컬럼명이랑 같아야한다.
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<UserRoleEntity> userRoleEntityList;

    // fetch 방식 2가지 EAGER : 즉시 가져오기 LAZY : 필요할 때만 가져오기  
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<TodoEntity> todoEntityList;
}
