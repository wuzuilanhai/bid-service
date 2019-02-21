create table t_bid(
  id VARCHAR(64) PRIMARY KEY COMMENT '主健',
  screen_id VARCHAR(64) COMMENT '场次ID',
  price BIGINT COMMENT '叫价价格',
  creator VARCHAR(64) COMMENT '创建者',
  create_time TIMESTAMP DEFAULT NOW() COMMENT '创建时间',
  editor VARCHAR(64) COMMENT '修改者',
  edit_time TIMESTAMP DEFAULT NOW() COMMENT '修改时间',
  is_delete INT DEFAULT 0 COMMENT '删除标志'
) COMMENT '叫价表' CHARSET utf8mb4;