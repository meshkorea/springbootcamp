package com.vroong.product.adapter.in.rest.mapper;

import com.vroong.product.domain.Example;
import com.vroong.product.rest.ExampleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface ExampleMapper extends DtoMapper<ExampleDto, Example> {

  @Override
  @Mapping(source = "id", target = "exampleId")
  ExampleDto toDto(Example entity);
}
