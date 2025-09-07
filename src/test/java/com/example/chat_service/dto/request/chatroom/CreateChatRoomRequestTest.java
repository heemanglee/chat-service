package com.example.chat_service.dto.request.chatroom;

import com.example.chat_service.domain.JoinPolicy;
import com.example.chat_service.domain.RoomStatus;
import com.example.chat_service.domain.RoomType;
import com.example.chat_service.domain.Visibility;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateChatRoomRequestTest {

    static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void title_blank_면_NotBlank에_걸린다() {
        String title = ""; // @NotBlank 위반
        String status = "ACTIVE";
        String type = "CHANNEL";
        String visibility = "PUBLIC";
        int memberLimit = 1;

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title(title)
                .status(status)
                .type(type)
                .visibility(visibility)
                .memberLimit(memberLimit)
                .build();

        // 검증 실행
        Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

        assertThat(violations).anySatisfy(v -> {
            assertThat(v.getPropertyPath().toString()).isEqualTo("title");
            assertThat(v.getMessage()).isEqualTo("title은 필수 값입니다");
        });
    }

    @Test
    void title_이_20_글자를_초과할_수_없다() {
        String title = "a".repeat(21); // @Size(20) 위반
        String status = "ACTIVE";
        String type = "CHANNEL";
        String visibility = "PUBLIC";
        int memberLimit = 1;

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title(title)
                .status(status)
                .type(type)
                .visibility(visibility)
                .memberLimit(memberLimit)
                .build();

        // 검증 실행
        Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

        assertThat(violations).anySatisfy(v -> {
            assertThat(v.getPropertyPath().toString()).isEqualTo("title");
            assertThat(v.getMessage()).isEqualTo("title은 20글자 이하이어야 합니다");
        });
    }

    @Test
    void status는_소문자를_사용할_수_없다() {
        String title = "test";
        String status = "active"; // 허용되지 않는 값
        String type = "CHANNEL";
        String visibility = "PUBLIC";
        int memberLimit = 1;

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title(title)
                .status(status)
                .type(type)
                .visibility(visibility)
                .memberLimit(memberLimit)
                .build();

        Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

        assertThat(violations).anySatisfy(v -> {
            assertThat(v.getPropertyPath().toString()).isEqualTo("status");
            assertThat(v.getInvalidValue()).isEqualTo("active");
        });
    }

    @Test
    void status는_허용된_값이면_통과한다() {
        RoomStatus[] statuses = RoomStatus.values();

        for (RoomStatus roomStatus : statuses) {
            String status = roomStatus.name();
            CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                    .title("title")
                    .status(status)
                    .type("CHANNEL")
                    .visibility("PUBLIC")
                    .memberLimit(1)
                    .build();

            Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }
    }

    @Test
    void type은_허용된_값이면_통과한다() {
        RoomType[] roomTypes = RoomType.values();

        for (RoomType roomType : roomTypes) {
            String type = roomType.name();

            Set<ConstraintViolation<String>> violations = validator.validate(type);

            assertThat(violations).isEmpty();
        }
    }

    @Test
    void visibility는_허용된_값이면_통과한다() {
        Visibility[] visibilities = Visibility.values();

        for (Visibility visibility : visibilities) {
            Set<ConstraintViolation<String>> violations = validator.validate(visibility.name());
            assertThat(violations).isEmpty();
        }
    }

    @Test
    void joinPolcy는_허용된_값이면_통과한다() {
        JoinPolicy[] joinPolicies = JoinPolicy.values();

        for (JoinPolicy joinPolicy : joinPolicies) {
            Set<ConstraintViolation<String>> violations = validator.validate(joinPolicy.name());
            assertThat(violations).isEmpty();
        }
    }

    @Test
    void 방에_한명_이상_참여하지_않으면_실패한다() {
        String title = "test";
        String status = "active"; // 허용되지 않는 값
        String type = "CHANNEL";
        String visibility = "PUBLIC";
        int enterCount = 0;

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title("title")
                .status(status)
                .type(type)
                .visibility(visibility)
                .memberLimit(enterCount)
                .build();

        Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

        assertThat(violations).anySatisfy(v -> {
            assertThat(v.getPropertyPath().toString()).isEqualTo("memberLimit");
        });
    }

    @Test
    void 방의_인원수가_100명을_초과하면_실패한다() {
        String title = "test";
        String status = "active"; // 허용되지 않는 값
        String type = "CHANNEL";
        String visibility = "PUBLIC";
        int enterCount = 101;

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .title("title")
                .status(status)
                .type(type)
                .visibility(visibility)
                .memberLimit(enterCount)
                .build();

        Set<ConstraintViolation<CreateChatRoomRequest>> violations = validator.validate(request);

        assertThat(violations).anySatisfy(v -> {
            assertThat(v.getPropertyPath().toString()).isEqualTo("memberLimit");
        });
    }
}