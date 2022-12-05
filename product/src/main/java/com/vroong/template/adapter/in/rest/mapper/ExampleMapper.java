package com.vroong.template.adapter.in.rest.mapper;

import com.vroong.template.domain.Example;
import com.vroong.template.rest.ExampleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface ExampleMapper extends DtoMapper<ExampleDto, Example> {

  @Override
  @Mapping(source = "id", target = "exampleId")
  ExampleDto toDto(Example entity);
}
