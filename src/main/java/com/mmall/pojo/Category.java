package com.mmall.pojo;

import lombok.*;

import java.util.Date;

//要重写equals和hashcode方法，因为这里判断相同只需要id相同

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;


}