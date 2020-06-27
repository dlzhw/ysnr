package com.zhw.mes.logic.impl;


import com.zhw.mes.domain.Product;
import com.zhw.mes.logic.ProductService;
import com.zhw.mes.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }
}
