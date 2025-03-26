/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.common.api;

import cn.hutool.core.util.IdUtil;
import com.obast.charer.common.utils.MapstructUtils;
import lombok.*;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Longjun.Tu
 * @description:
 * @date:created in 2023/5/10 23:15
 * @modificed by:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequest<T> extends Request<T> implements Serializable {

    /**
     * 分页大小
     */
    @Min(1)
    private Integer pageSize;

    /**
     * 当前页数
     */
    @Min(1)
    private Integer pageNum;

    /**
     * 排序 key为排序字段名 value为排序方向方向desc或者asc
     */
    private Map<String, String> sortMap;

    /**
     * 当前记录起始索引 默认值
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 每页显示记录数 默认值
     */
    public static final int DEFAULT_PAGE_SIZE = 30;


    public static <T> PageRequest<T> of(T data) {
        PageRequest<T> pageRequest = new PageRequest<>();
        pageRequest.setPageSize(DEFAULT_PAGE_SIZE);
        pageRequest.setPageNum(DEFAULT_PAGE_NUM);
        pageRequest.setData(data);
        pageRequest.setRequestId(IdUtil.simpleUUID());
        return pageRequest;
    }

    public <DTO> PageRequest<DTO> to(Class<DTO> dtoClass) {
        PageRequest<DTO> pageRequest = new PageRequest<>();
        if (Objects.nonNull(getData())) {
            pageRequest.setData(MapstructUtils.convert(getData(), dtoClass));
        }
        pageRequest.setPageNum(this.getPageNum());
        pageRequest.setPageSize(this.getPageSize());
        pageRequest.setRequestId(this.getRequestId());
        pageRequest.setSortMap(this.getSortMap());
        return pageRequest;
    }

    public Integer getPageSize() {
        return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPageNum() {
        return pageNum == null ? DEFAULT_PAGE_NUM : pageNum;
    }

    public Integer getOffset() {
        return (getPageNum() - 1) * getPageSize();
    }
}
