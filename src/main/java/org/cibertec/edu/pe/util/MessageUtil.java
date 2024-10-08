package org.cibertec.edu.pe.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUtil {
    private String message;
    private Date timestamp;
    private int status;
    private Object result;
}
