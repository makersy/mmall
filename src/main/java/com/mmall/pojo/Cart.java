package com.mmall.pojo;

import lombok.*;

import java.util.Date;

/*
* 使用lombok插件简化bean的getter、setter、toString等方法
* */

// @Data -- 注解在类上；提供类所有属性的 getting 和 setting 方法，
// 此外还提供了equals、canEqual、hashCode、toString 方法
@Getter
@Setter
@NoArgsConstructor  //无参构造器
@AllArgsConstructor //全参构造器
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Date createTime;

    private Date updateTime;


}