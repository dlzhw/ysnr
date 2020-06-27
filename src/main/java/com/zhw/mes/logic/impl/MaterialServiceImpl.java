package com.zhw.mes.logic.impl;


import com.zhw.mes.domain.Material;
import com.zhw.mes.logic.MaterailService;
import com.zhw.mes.mapper.MaterailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MaterialServiceImpl implements MaterailService {

    @Autowired
    private MaterailMapper materailMapper;

    @Override
    public List<Material> findByNo(String no) {
       return materailMapper.findByNo(no);
    }
}
