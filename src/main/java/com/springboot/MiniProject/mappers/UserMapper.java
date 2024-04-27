package com.springboot.MiniProject.mappers;

import com.springboot.MiniProject.config.UserInfoDetails;
import com.springboot.MiniProject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {

    UserInfoDetails toUserDto(User user);

}