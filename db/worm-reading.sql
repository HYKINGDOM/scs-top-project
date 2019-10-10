
-- ----------------------------
-- 1、书籍基本信息表
-- ----------------------------
drop table if exists book_info;
create table book_info (
  bookId 	 		          bigint(20) 	  not null 			    comment '书籍ID 第一存入的时候生成',
  bookName 		          varchar(50) 	default null 			comment '书籍名',
  bookType              varchar(30) 	default null 			comment '书籍类型',
  latestChapters	 	    varchar(100)  default null 			comment '最新章节',
  author  	 			      varchar(40) 	default null 			comment '作者',
  articleLength   		  varchar(30) 	default null   		comment '字数',
  updateTime           	varchar(30)   default null      comment '最后一次更新时间',
  currentState         	varchar(10)   default null      comment '书籍当前状态',
  articleCategory       varchar(30)   default null      comment '文章类别',
  collectionNum 	      varchar(30)   default null      comment '收藏数',
  totalClick            varchar(30)   default null      comment '总点击数',
  totalRecommendedNum   varchar(30)   default null      comment '总推荐数',
  content 		    	    varchar(2000) default null			comment '书籍简介',
  bookAddressUrl 		    varchar(500) 	default null			comment '书籍简介地址',
  bookIndexUrl 		      varchar(500) 	default null			comment '书籍的目录地址',
  bookImageUrl 		      varchar(500) 	default null			comment '书籍封面地址',
  bookSource            varchar(100)  default null      comment '书籍来源',
  crawlingTime          datetime     	default null      comment '更新时间',
  primary key (bookId)
) engine=innodb auto_increment=100 default charset=utf8 comment = '书籍基本信息表';



-- ----------------------------
-- 2、书籍章节内容存储表
-- ----------------------------
drop table if exists book_chapter;
create table book_chapter (
  id 	 		    	integer(20) 	not null auto_increment			    comment '书籍主键ID',
  bookId 	 		    bigint(20) 	    not null 			    comment '书籍ID 关联book_info表',
  chapterNameNum	 	integer(100)    default null 			comment '章节名编号',
  chapterName  	 		varchar(40) 	default null 			comment '章节名',
  chapterContentNum   	varchar(30) 	default null   			comment '章节字数',
  chapterContent        varchar(30)     default null            comment '章节内容',
  updateTime         	datetime     	default null            comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '书籍章节内容表';


-- ----------------------------
-- 3、定时任务执行时间表
-- ----------------------------
drop table if exists schedule_task_job;
create table schedule_task_job (
  id 	 		    	integer(20) 	not null auto_increment		comment '定时任务主键ID',
  scheduleTaskName 	 	varchar(30) 	default null 			    comment '定时任务名',
  scheduleTaskNum	 	integer(100)    default null 				comment '定时任务编号',
  scheduleTaskCorn  	varchar(20) 	default null 				comment '定时任务时间解析',
  createby   			varchar(30) 	default null   				comment '创建人',
  createTime        	datetime        default null            	comment '创建时间',
  updateTime         	datetime     	default null            	comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=100 default charset=utf8 comment = '定时任务执行时间表';




-- ----------------------------
-- 4、code中的review信息表
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
-- 5、评审代码内容表
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
-- 6、用户信息表
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

​
