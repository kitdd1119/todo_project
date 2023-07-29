package com.example.my.domain.todo.service;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.dto.ResponseDTO;
import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV1 {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getTodoTableData(LoginUserDTO loginUserDTO) {
        // 리파지토리에서 삭제되지 않은 할 일 목록 찾기
        List<TodoEntity> todoList = todoRepository.findByDeleteDateIsNull();

        // 응답 데이터로 리턴하기
        return ResTodoTableDTO.of(todoList);
    }

    @Transactional
    public ResponseEntity<?> insertTodoTableData(ReqTodoTableInsertDTO dto, LoginUserDTO loginUserDTO) {
        // 할 일을 입력했는지 확인
        if (dto.getTodo() == null || dto.getTodo().getContent() == null || dto.getTodo().getContent().isEmpty()) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("할 일을 입력해주세요.")
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }

        // 리파지토리에서 유저 기본키로 삭제되지 않은 유저 찾기
        Optional<UserEntity> userEntityOptional = userRepository.findById(loginUserDTO.getUser().getId());
        UserEntity userEntity = userEntityOptional.orElse(null);

        if (userEntity == null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(2)
                            .message("존재하지 않는 유저입니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }

        // 할 일 엔티티 생성
        TodoEntity todoEntity = TodoEntity.builder()
                .content(dto.getTodo())
                .doneYn('N')
                .userEntity(userEntity)
                .createDate(LocalDateTime.now())
                .build();

        // 할 일 엔티티 저장
        todoRepository.save(todoEntity);

        // 응답 데이터로 리턴하기
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 추가에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateTodoTableData(Long todoIdx, ReqTodoTableUpdateDoneYnDTO dto, LoginUserDTO loginUserDTO) {
        // 리파지토리에서 할 일 찾기
        Optional<TodoEntity> todoEntityOptional = todoRepository.findById(todoIdx);
        TodoEntity todoEntity = todoEntityOptional.orElse(null);

        if (todoEntity == null || todoEntity.getDeleteDate() != null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("존재하지 않는 할 일 입니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }

        // 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다.)
        if (!todoEntity.getUserEntity().getId().equals(loginUserDTO.getUser().getId())) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(2)
                            .message("권한이 없습니다.")
                            .build(),
                    HttpStatus.UNAUTHORIZED);
        }

        // 할 일 doneYn 업데이트
        todoEntity.setDoneYn(LocalDateTime.now());
        todoRepository.save(todoEntity);

        // 응답 데이터로 리턴하기
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteTodoTableData(Long todoIdx, LoginUserDTO loginUserDTO) {
        // 리파지토리에서 할 일 찾기
        Optional<TodoEntity> todoEntityOptional = todoRepository.findById(todoIdx);
        TodoEntity todoEntity = todoEntityOptional.orElse(null);

        if (todoEntity == null || todoEntity.getDeleteDate() != null) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(1)
                            .message("존재하지 않는 할 일 입니다.")
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }

        // 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다.)
        if (!todoEntity.getUserEntity().getId().equals(loginUserDTO.getUser().getId())) {
            return new ResponseEntity<>(
                    ResponseDTO.builder()
                            .code(2)
                            .message("권한이 없습니다.")
                            .build(),
                    HttpStatus.UNAUTHORIZED);
        }

        // 할 일 deleteDate 업데이트
        todoEntity.setDeleteDate(LocalDateTime.now());
        todoRepository.save(todoEntity);

        // 응답 데이터로 리턴하기
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .code(0)
                        .message("할 일 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }
}
