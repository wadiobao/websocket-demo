package com.project.library_management.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LendRequest {
    @NotNull(message = "{validation.document_id.not_null}")
    private Long documentId;

    @NotNull(message = "{validation.member_id.not_null}")
    private Long memberId;
}
