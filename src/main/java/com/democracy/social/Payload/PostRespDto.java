package com.democracy.social.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRespDto {
    private Long id;
    private String title;
    private String content;
    private String postedBy;

}
