package com.example.starterapp.POJO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "用户实体类")
public class User {

    @Id
    @ApiModelProperty(value = "用户Id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(value = "用户姓名", dataType = "String", required = true)
    private String username;

    @ApiModelProperty(value = "用户密码", dataType = "String", required = true)
    private String password;

    @ApiModelProperty(value = "用户性别", dataType = "Integer")
    private Integer gender;
}
