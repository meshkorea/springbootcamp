package com.vroong.order.adapter.in.rest.mapper;

import com.vroong.order.domain.Example;
import com.vroong.order.rest.ExampleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface ExampleMapper extends DtoMapper<ExampleDto, Example> {

  @Override
  @Mapping(source = "id", target = "exampleId")
  ExampleDto toDto(Example entity);
}
