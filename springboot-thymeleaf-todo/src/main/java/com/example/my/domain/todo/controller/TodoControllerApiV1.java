package com.example.my.domain.todo.controller;

import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.domain.todo.service.TodoServiceApiV1;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // java.util.List를 import합니다.

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoControllerApiV1 {

    private final TodoServiceApiV1 todoServiceApiV1;

    @GetMapping
    public ResponseEntity<List<ResTodoTableDTO>> getTodoTableData() {
        // 서비스에서 할 일 목록 가져오기
        List<ResTodoTableDTO> todoList = todoServiceApiV1.getTodoTableData();
        return ResponseEntity.ok(todoList);
    }

    @PostMapping
    public ResponseEntity<?> insertTodoTableData(
            @RequestBody ReqTodoTableInsertDTO dto,
            HttpSession session
    ) {
        // session에 dto가 없으면 BadRequest 처리
        if (dto == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.badRequest().body("BadRequest: Required data not found in the session.");
        }
    
        // 서비스에서 할 일 추가하기
        ResponseEntity<?> insertTodoTableData = todoServiceApiV1.insertTodoTableData(dto);
        return insertTodoTableData;
    }

    @PutMapping("/{todoIdx}")
    public ResponseEntity<?> updateTodoTableData(
            @PathVariable Long todoIdx,
            @RequestBody ReqTodoTableUpdateDoneYnDTO dto,
            HttpSession session
    ) {
        // 서비스에서 할 일 완료 수정하기
        ResponseEntity<?> updateTodoTableData = todoServiceApiV1.updateTodoTableData(todoIdx, dto);
        return updateTodoTableData;
    }

    @DeleteMapping("/{todoIdx}")
    public ResponseEntity<?> deleteTodoTableData(
            @PathVariable Long todoIdx,
            HttpSession session
    ) {
        // 서비스에서 할 일 삭제하기
        ResponseEntity<?> deleteTodoTableData = todoServiceApiV1.deleteTodoTableData(todoIdx);
        return deleteTodoTableData;
    }
}
