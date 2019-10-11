
-- ----------------------------
-- 1、code中的review信息表
-- ----------------------------
drop table if exists scs_code_review_info;
create table scs_code_review_info (
   codeReviewId         integer(20) 	not null auto_increment		comment '主键ID',
   codeReviewTitle      varchar(200) 	default null            	comment '列表内容标题',
   codeReviewDescript   varchar(1000) 	default null            	comment '列表内容描述',
   pointRatio           integer(100) 	default null            	comment '点赞数',
   codeReviewCount      integer(100) 	default null            	comment '列表评论数',
   gitAddressUrl        varchar(200) 	default null            	comment 'git代码地址',
   userName             varchar(100) 	default null            	comment '用户名',
   codeType             varchar(100) 	default null            	comment '定时任务主键ID',
   createBy             varchar(30) 	default null   				comment '创建人',
   createTime           datetime        default null            	comment '创建时间',
   updateBy             varchar(30) 	default null   				comment '创建人',
   updateTime           datetime     	default null            	comment '更新时间',
   primary key (codeReviewId)
) engine=innodb auto_increment=100 default charset=utf8 comment = 'code中的review信息表';


-- ----------------------------
-- 2、评审代码内容表
-- ----------------------------
drop table if exists scs_code_review_content;
create table scs_code_review_content (
  id                   integer(20) 	    not null auto_increment		comment '主键ID',
  codeReviewId         integer(20) 	    not null            		comment '关联的详细信息表id',
  codeText             text          	default null            	comment '代码内容',
  createBy             varchar(30) 	    default null   				comment '创建人',
  createTime           datetime         default null            	comment '创建时间',
  updateBy             varchar(30) 	    default null   				comment '创建人',
  updateTime           datetime     	default null            	comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '评审代码内容表';


-- ----------------------------
-- 3、用户信息表
-- ----------------------------
drop table if exists scs_user_info;
create table scs_user_info (
  id                   integer(20) 	    not null auto_increment		comment '主键ID',
  userName             integer(20) 	    not null            		comment '关联的详细信息表id',
  password             integer(20) 	    not null            		comment '关联的详细信息表id',
  salt                 integer(20) 	    not null            		comment '关联的详细信息表id',
  mobile               integer(20) 	    not null            		comment '关联的详细信息表id',
  email                integer(20) 	    not null            		comment '关联的详细信息表id',
  emailActStatus       integer(20) 	    not null            		comment '关联的详细信息表id',
  status               integer(20) 	    not null            		comment '关联的详细信息表id',
  photoAddress         integer(20) 	    not null            		comment '关联的详细信息表id',
  codeText             text          	default null            	comment '代码内容',
  createBy             varchar(30) 	    default null   				comment '创建人',
  createTime           datetime         default null            	comment '创建时间',
  updateBy             varchar(30) 	    default null   				comment '创建人',
  updateTime           datetime     	default null            	comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '评审代码内容表';
