package com.self.framework.http;

import com.self.framework.base.BaseReult;
import com.self.framework.cenum.HttpResultEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @des 后台列表分页返回结果
 * @author qiuhang
 * @version v.1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> extends BaseReult {
    private Long total;

    private List<T> rows;

    public static <V> PageResult<V> pageResult(HttpResultEnum httpResultEnum, List<V> rows, Long total){
        PageResult<V> pageResult = new PageResult<>();
        pageResult.setRows(rows);
        pageResult.setTotal(total);
        pageResult.setCode(httpResultEnum.getCode());
        pageResult.setDescribe(httpResultEnum.getMessage());
        return pageResult;
    }
}
