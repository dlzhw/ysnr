package com.zhw.mes.logic;



import com.zhw.mes.domain.Material;

import java.util.List;

public interface MaterailService {
    List<Material> findByNo(String no);
}
