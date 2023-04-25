package com.visual.face.search.server.service.impl;

import com.visual.face.search.server.mapper.ImageDataMapper;
import com.visual.face.search.server.model.ImageData;
import com.visual.face.search.server.service.api.ImageDataService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


@Service("visualImageDataService")
public class ImageDataServiceImpl implements ImageDataService {

    @Resource
    private ImageDataMapper imageDataMapper;

    @Override
    public Boolean insert(String table, ImageData record) {
        return imageDataMapper.insert(table, record) > 0;
    }
}
