package com.visual.face.search.server.service.base;

import java.util.UUID;
import org.springframework.util.DigestUtils;

public abstract class BaseService {
    public static final  String TABLE_PREFIX = "visual_search";
    public static final  String CHAR_UNDERLINE = "_";

    public String uuid(){
        String uuidStr = UUID.randomUUID().toString() + "|_|" + UUID.randomUUID().toString();
        return DigestUtils.md5DigestAsHex(uuidStr.getBytes());
    }

}
