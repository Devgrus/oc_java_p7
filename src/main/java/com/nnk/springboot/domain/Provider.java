package com.nnk.springboot.domain;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL, GITHUB;
}
