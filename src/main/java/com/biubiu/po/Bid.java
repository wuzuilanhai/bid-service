package com.biubiu.po;

import com.biubiu.util.KeyGenUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 张海彪
 * @create 2019-02-21 10:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_bid")
public class Bid {

    @Id
    @KeySql(genId = KeyGenUtil.class)
    private String id;

    private String screenId;

    private Long price;

    private String creator;

    private Date createTime;

    private String editor;

    private Date editTime;

    private Integer isDelete;

}
