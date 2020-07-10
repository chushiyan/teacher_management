package com.example.teacher.utils;

import java.io.Serializable;
import java.util.List;

public class PageResult<T>  implements Serializable {
    private Long total;
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }


}
