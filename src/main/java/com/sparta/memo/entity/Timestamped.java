package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // Timestamped를 상속하는 Entity는 createdAt와 modifiedAt 컬럼을 가진다.
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped { // 추상클래스

    @CreatedDate
    @Column(updatable = false) // 최초 생성시간만
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate // 조회한 Entity 객체의 값을 변경할 때 변경된 시간이 자동으로 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP) // 날짜(java.util.Date, java.util.Calendar)을 매핑할 때 사용 (DATE, TIME, TIMESTAMP)
    private LocalDateTime modifiedAt;
}