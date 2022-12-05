package com.vroong.delivery.adapter.in.rest.mapper;

import com.vroong.delivery.domain.Example;
import com.vroong.delivery.rest.ExampleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface ExampleMapper extends DtoMapper<ExampleDto, Example> {

  @Override
  @Mapping(source = "id", target = "exampleId")
  ExampleDto toDto(Example entity);
}
