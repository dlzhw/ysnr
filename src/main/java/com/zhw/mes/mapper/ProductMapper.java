package com.zhw.mes.mapper;


import com.zhw.mes.domain.Product;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    @Select("select * from tb_product")
    List<Product> findAll();
}
