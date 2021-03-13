package net.ujacha.urlshortening.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResPayload {

    private String message;

}
