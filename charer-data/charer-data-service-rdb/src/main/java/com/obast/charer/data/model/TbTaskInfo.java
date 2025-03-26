package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.model.task.TaskInfo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMapping;
import io.github.linpeilie.annotations.ReverseAutoMapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "task_info")
@AutoMapper(target = TaskInfo.class)
public class TbTaskInfo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "任务类型")
    private String type;

    /**
     * 表达式
     * 定时器使用cron表达式
     * 延时器使用延时时长（秒）
     */
    @ApiModelProperty(value = "表达式")
    private String expression;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String note;

    /**
     * 任务输出
     */
    @Column(columnDefinition = "text")
    @ApiModelProperty(value = "任务输出")
    @AutoMapping(ignore = true)
    @ReverseAutoMapping(ignore = true)
    private String actions;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")
    private String state;

    /**
     * 操作备注
     */
    @ApiModelProperty(value = "操作备注")
    private String reason;
}
