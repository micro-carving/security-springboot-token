package com.security.token.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Data
@EqualsAndHashCode
@ToString
public class BaseEntity implements Serializable {
    /**
     * 编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建的用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;

    /**
     * 创建部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createDept;

    /**
     * 创建时间（形如：年-月-日 时:分:秒）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;

    /**
     * 更新时间（形如：年-月-日 时:分:秒）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除，逻辑删除（形如：0：正常、1：已删除）
     */
    @TableLogic
    private Integer isDeleted;
}