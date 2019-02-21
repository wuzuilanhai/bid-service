package com.biubiu.util;

import tk.mybatis.mapper.genid.GenId;

/**
 * 全局主健生成工具类
 *
 * @author 张海彪
 * @create 2019-02-19 15:33
 */
public class KeyGenUtil implements GenId<String> {

    @Override
    public String genId(String table, String column) {
        return String.valueOf(IdWorker.idWorker.nextId());
    }

}
