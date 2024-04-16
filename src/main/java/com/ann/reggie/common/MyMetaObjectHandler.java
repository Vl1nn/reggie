package com.ann.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
//元对象处理器 作用:自动填充公共字段
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //自动填充创建时间和更新时间
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //创建人id 更新人id
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新自动填充
        //完善：改变updateUser固定值，使用ThreadLocal
        metaObject.setValue("updateTime", LocalDateTime.now());
        //获取ThreadLocal中的值
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
