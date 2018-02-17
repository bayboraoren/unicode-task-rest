package com.unico.rest.data.mapper;

import com.unico.rest.common.request.ParameterRequest;
import com.unico.rest.data.model.Parameter;
import org.mapstruct.Mapper;

/**
 * Created by baybora on 2/10/18.
 */
@Mapper(componentModel = "spring")
public interface ParametersMapper {
    ParameterRequest parameterToParameterRequest(Parameter parameter);
    Parameter parameterRequestToParameter(ParameterRequest parameterRequest);
}
