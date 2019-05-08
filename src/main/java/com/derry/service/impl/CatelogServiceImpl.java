package com.derry.service.impl;

import com.derry.dao.CatelogMapper;
import com.derry.pojo.Catelog;
import com.derry.service.CatelogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2017/5/9.
 */
@Service("catelogService")
public class CatelogServiceImpl implements CatelogService {

    @Resource
    private CatelogMapper catelogMapper;
    @Override
    public int getCount(Catelog catelog) {
        int count = catelogMapper.getCount(catelog);
        return count;
    }
    @Override
    public List<Catelog> getAllCatelog() {
        List<Catelog> catelogs = catelogMapper.getAllCatelog();
        return catelogs;
    }
    @Override
    public Catelog selectByPrimaryKey(Integer id){
        Catelog catelog = catelogMapper.selectByPrimaryKey(id);
        return catelog;
    }
    @Override
    public int updateByPrimaryKey(Catelog catelog) {
        return  catelogMapper.updateByPrimaryKey(catelog);
    }
    @Override
    public int updateCatelogNum(Integer id,Integer number) {
        return catelogMapper.updateCatelogNum(id,number);
    }
}
