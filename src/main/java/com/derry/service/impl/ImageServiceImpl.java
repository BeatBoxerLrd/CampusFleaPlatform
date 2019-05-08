package com.derry.service.impl;

import com.derry.dao.ImageMapper;
import com.derry.pojo.Image;
import com.derry.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2017/5/12.
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageMapper imageMapper;
    @Override
    public int insert(Image record) {
        return imageMapper.insert(record);
    }
    @Override
    public List<Image> getImagesByGoodsPrimaryKey(Integer goodsId) {
        List<Image> image = imageMapper.selectByGoodsPrimaryKey(goodsId);
        return image;
    }
    @Override
    public int deleteImagesByGoodsPrimaryKey(Integer goodsId) {
        return imageMapper.deleteImagesByGoodsPrimaryKey(goodsId);
    }
}
