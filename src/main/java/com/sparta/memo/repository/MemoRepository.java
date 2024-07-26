package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {

    //ModifiedAt 데이터 기준으로 내림차순으로 정렬된 전체 데이터 찾기
    List<Memo> findAllByOrderByModifiedAtDesc();
}