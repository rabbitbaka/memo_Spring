package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //client에서 html이 아닌 데이터만 전송하기 때문에 RestController사용
@RequestMapping("/api")
public class MemoController {


    private final Map<Long, Memo> memoList = new HashMap<>(); //Map의 <key(id), value(실제데이터)>


    @PostMapping("/memos") //MemoResponseDto : 객체를 받는 dto , MemoRequestDto : 객체를 보내는 dto
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1; //key값중에서 가장 큰값 + 1
        memo.setId(maxId); //id넣기

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() { //List형식으로 메모들 받기
        // Map To List
        List<MemoResponseDto> responseList = memoList.values().stream() //하나씩 뽑기
                .map(MemoResponseDto::new).toList(); //생성자를 수행해서 모으기

        return responseList;
    }

    @PutMapping("/memos/{id}") //update하는 데이터는 put으로 받기
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        //update한 id만 받아올 거라 Long으로 설정, json으로 받아오기 때문에 @RequestBody 사용

        //해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            //해당 메모 가져오기
            Memo memo = memoList.get(id);

            //memo 수정
            memo.update(requestDto);
            return memo.getId();

        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }

    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        //해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            //해당 메모를 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

}
