package com.example.rensourcevideorentals.dtos.responseBody;

import lombok.*;


@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplate implements Message {

    private String summary;

    private String detail;

    private Message.Severity severity;
}
