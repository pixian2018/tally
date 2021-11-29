use `wx`;

drop TABLE if EXISTS `record`;
CREATE TABLE `record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录修改时间,如果时间是1970年则表示纪录未修改',
  `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
  `modifier` varchar(20) NOT NULL DEFAULT '0' COMMENT '修改人,如果为0则表示纪录未修改',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '场次名称',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次名称表';


drop TABLE if EXISTS record_detail;
CREATE TABLE `record_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录修改时间,如果时间是1970年则表示纪录未修改',
  `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
  `modifier` varchar(20) NOT NULL DEFAULT '0' COMMENT '修改人,如果为0则表示纪录未修改',
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `group_no` int(11) NOT NULL DEFAULT '0' COMMENT '分组',
  `player_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家id',
  `money` decimal(10,1) NOT NULL DEFAULT '0.0' COMMENT '金额',
  PRIMARY KEY (`id`),
  KEY `record_id` (`record_id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次明细表';

drop TABLE if EXISTS record_player;
CREATE TABLE `record_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
  `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录修改时间,如果时间是1970年则表示纪录未修改',
  `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
  `modifier` varchar(20) NOT NULL DEFAULT '0' COMMENT '修改人,如果为0则表示纪录未修改',
  `record_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '场次id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '玩家名称',
  `order_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_join` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否加入（1：加入，0：未加入）',
  PRIMARY KEY (`id`),
  KEY `record_id` (`record_id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='场次玩家表';

-- drop TABLE if EXISTS player;
-- CREATE TABLE `player` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
--   `is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除,N:未删除，Y:删除',
--   `gmt_create` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录创建时间',
--   `gmt_modified` datetime NOT NULL DEFAULT '1970-01-01 12:00:00' COMMENT '记录修改时间,如果时间是1970年则表示纪录未修改',
--   `creator` varchar(20) NOT NULL DEFAULT '0' COMMENT '创建人，0表示无创建人值',
--   `modifier` varchar(20) NOT NULL DEFAULT '0' COMMENT '修改人,如果为0则表示纪录未修改',
--   `name` varchar(255) NOT NULL DEFAULT '0' COMMENT '玩家名称',
--   PRIMARY KEY (`id`),
--   KEY `name` (`name`),
--   KEY `creator` (`creator`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='玩家表';
