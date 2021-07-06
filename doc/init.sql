drop TABLE if EXISTS record;
CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '创建人',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '场次名称',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次名称表';

drop TABLE if EXISTS record_detail;
CREATE TABLE `record_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家id',
  `money` varchar(255) NOT NULL DEFAULT '0' COMMENT '金额',
  PRIMARY KEY (`id`),
  KEY `record_id` (`record_id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次明细表';

drop TABLE if EXISTS record_player;
CREATE TABLE `record_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `name` varchar(255) NOT NULL DEFAULT '0' COMMENT '玩家名称',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次玩家表';