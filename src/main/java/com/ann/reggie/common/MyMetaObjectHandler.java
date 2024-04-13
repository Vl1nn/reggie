package com.ann.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

//元对象处理器 作用:自动填充公共字段
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //自动填充创建时间和更新时间
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //创建人id 更新人id
        metaObject.setValue("createUser",1L);
        metaObject.setValue("updateUser",1L);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
