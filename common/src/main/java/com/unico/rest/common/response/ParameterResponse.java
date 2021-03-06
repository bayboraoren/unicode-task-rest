package com.unico.rest.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by baybora on 2/14/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParameterResponse {
    private Long id;
    private Integer parameter1;
    private Integer parameter2;
    private Date insertTimestamp;
}
