package com.zhw.mes.mapper;


import com.zhw.mes.domain.Material;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterailMapper {

    @Select("select * from tb_material where mno = #{mno}")
    List<Material> findByNo(String mno);
}
