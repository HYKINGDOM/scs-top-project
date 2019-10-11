/*
Navicat MySQL Data Transfer

Source Server         : 47.107.127.244_3306
Source Server Version : 50722
Source Host           : 47.107.127.244:3306
Source Database       : worm_reading

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-10-11 20:51:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_chapter
-- ----------------------------
DROP TABLE IF EXISTS `book_chapter`;
CREATE TABLE `book_chapter` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '书籍主键ID',
  `bookId` bigint(20) NOT NULL COMMENT '书籍ID 关联book_info表',
  `chapterNameNum` int(100) DEFAULT NULL COMMENT '章节名编号',
  `chapterName` varchar(40) DEFAULT NULL COMMENT '章节名',
  `chapterContentNum` varchar(30) DEFAULT NULL COMMENT '章节字数',
  `chapterContent` varchar(30) DEFAULT NULL COMMENT '章节内容',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书籍章节内容表';

-- ----------------------------
-- Records of book_chapter
-- ----------------------------

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `bookId` bigint(20) NOT NULL COMMENT '书籍ID 第一存入的时候生成',
  `bookName` varchar(50) DEFAULT NULL COMMENT '书籍名',
  `bookType` varchar(30) DEFAULT NULL COMMENT '书籍类型',
  `latestChapters` varchar(100) DEFAULT NULL COMMENT '最新章节',
  `author` varchar(40) DEFAULT NULL COMMENT '作者',
  `articleLength` varchar(30) DEFAULT NULL COMMENT '字数',
  `updateTime` varchar(30) DEFAULT NULL COMMENT '最后一次更新时间',
  `currentState` varchar(10) DEFAULT NULL COMMENT '书籍当前状态',
  `articleCategory` varchar(30) DEFAULT NULL COMMENT '文章类别',
  `collectionNum` varchar(30) DEFAULT NULL COMMENT '收藏数',
  `totalClick` varchar(30) DEFAULT NULL COMMENT '总点击数',
  `totalRecommendedNum` varchar(30) DEFAULT NULL COMMENT '总推荐数',
  `content` varchar(2000) DEFAULT NULL COMMENT '书籍简介',
  `bookAddressUrl` varchar(500) DEFAULT NULL COMMENT '书籍简介地址',
  `bookIndexUrl` varchar(500) DEFAULT NULL COMMENT '书籍的目录地址',
  `bookImageUrl` varchar(500) DEFAULT NULL COMMENT '书籍封面地址',
  `bookSource` varchar(100) DEFAULT NULL COMMENT '书籍来源',
  `crawlingTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书籍基本信息表';

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('2544', '校花的贴身高手', '都市言情', '第7801章 大获全胜', '鱼人二代', '18936626字', '2019-05-28', '连载中', null, '24720', '6632899', '115189', '一个大山里走出来的绝世高手，一块能预知未来的神秘玉佩…… 林逸是一名普通的高三学生，不过，他还有身负另外一个重任，那就是泡校花！而且还是奉校花老爸之命！ 虽然林逸很不想跟这位难伺候的大小姐打交道，但是长辈之命难违抗，他不得不千里迢迢的转学到了松山市第一高中，给大小姐鞍前马后的当跟班……于是，史上最牛逼的跟班出现了大小姐的贴身高手！ 看这位跟班如何发家致富偷小姐，开始他奉旨泡妞牛X闪闪的人生…… 本书有点儿纯……也有点儿小暧昧……', 'https://www.x23us.com/book/2544', 'https://www.x23us.com/html/2/2544/', 'https://www.x23us.com/files/article/image/2/2544/2544s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:14');
INSERT INTO `book_info` VALUES ('10913', '混沌剑神', '武侠修真', '第两千六百七十五章 鹤芊芊的拉拢', '心星逍遥', '8209343字', '2019-07-17', '连载中', null, '26439', '1290159', '73422', '剑尘，江湖中公认的第一高手，一手快剑法出神入化，无人能破，当他与消失百年的绝世高手独孤求败一战之后，身死而亡。 死后，剑尘的灵魂转世来到了一个陌生的世界，并且飞快的成长了起来，最后因仇家太多，被仇家打成重伤，在生死关头灵魂发生异变，从此以后，他便踏上了一条完全不同的剑道修炼之路，最终成为一代剑神。 逍遥初来乍到，喜欢《混沌剑神》的朋友可以到17K网站来阅读，就不用去看盗版的了。 本书实力体系，由低至高——圣者，大圣者，圣师，大圣师，大地圣师，天空圣师，圣王，圣皇，圣帝。', 'https://www.x23us.com/book/10913', 'https://www.x23us.com/html/10/10913/', 'https://www.x23us.com/files/article/image/10/10913/10913s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:16');
INSERT INTO `book_info` VALUES ('25754', '武炼巅峰', '历史军事', '第四千七百九十七章 真是好弟弟好妹妹', '莫默', '18279378字', '2019-07-04', '连载中', null, '26701', '5103056', '210252', '武之巅峰，是孤独，是寂寞，是漫漫求索，是高处不胜寒 逆境中成长，绝地里求生，不屈不饶，才能堪破武之极道。 凌霄阁试炼弟子兼扫地小厮杨开偶获一本无字黑书，从此踏上漫漫武道。友情提示：这是一个持久的男人的故事，这也是一个淫者见淫，智者见智的故事。 做男人，当持久！新书上传，各种求啊！', 'https://www.x23us.com/book/25754', 'https://www.x23us.com/html/25/25754/', 'https://www.x23us.com/files/article/image/25/25754/25754s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:03');
INSERT INTO `book_info` VALUES ('28357', '修罗武神', '历史军事', '第四千零二十八章 血脉逆天', '善良的蜜蜂', '9941990字', '2019-07-23', '连载中', null, '24839', '4049322', '66364', '论潜力，不算天才，可玄功武技，皆可无师自通。 论魅力，千金小姐算什么，妖女圣女，都爱我欲罢不能。 论实力，任凭你有万千至宝，但定不敌我界灵大军。 我是谁？天下众生视我为修罗，却不知，我以修罗成武神。 等级：灵武，元武，玄武，天武，武君，武王，武帝，武祖.... 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓 【修罗殿】铁杆①群：215777237（已满） 【修罗殿】铁杆②群：307953758（已满） 【修罗殿】铁杆③群：224035061（已满） 【修罗殿】铁杆④群：118453747（已满）', 'https://www.x23us.com/book/28357', 'https://www.x23us.com/html/28/28357/', 'https://www.x23us.com/files/article/image/28/28357/28357s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:08');
INSERT INTO `book_info` VALUES ('50658', '永夜君王', '玄幻魔法', '终章 美丽新世界', '烟雨江南', '5319786字', '2019-01-10', '连载中', null, '14067', '1040059', '99933', '千夜自困苦中崛起，在背叛中坠落。 自此一个人，一把枪，行在永夜与黎明之间，却走出一段传奇。 若永夜注定是他的命运，那他也要成为主宰的王。', 'https://www.x23us.com/book/50658', 'https://www.x23us.com/html/50/50658/', 'https://www.x23us.com/files/article/image/50/50658/50658s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:05');
INSERT INTO `book_info` VALUES ('51230', '儒道至圣', '玄幻魔法', '第3313章 新世界（大结局）', '永恒之火', '8503155字', '2019-07-19', '连载中', null, '26519', '2662748', '321312', '这是一个读书人掌握天地之力的世界。才气在身，诗可杀敌，词能灭军，文章安天下。秀才提笔，纸上谈兵；举人杀敌，出口成章；进士一怒，唇枪舌剑。圣人驾临，口诛笔伐，可诛人，可判天子无道，以一敌国。此时，圣院把持文位，国君掌官位，十国相争，蛮族虎视，群妖作乱。此时，无唐诗大兴，无宋词鼎盛，无创新文章，百年无新圣。一个默默无闻的寒门子弟，被人砸破头后，挟传世诗词，书惊圣文章，踏上至圣之路。感谢阅文官方书评团提供书评支持！', 'https://www.x23us.com/book/51230', 'https://www.x23us.com/html/51/51230/', 'https://www.x23us.com/files/article/image/51/51230/51230s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:11');
INSERT INTO `book_info` VALUES ('55304', '全职法师', '玄幻魔法', '第3030章 就在这里修行吧', '乱', '7384527字', '2019-07-22', '连载中', null, '17174', '3118415', '76973', '一觉醒来，世界大变。熟悉的高中传授的是魔法，告诉大家要成为一名出色的魔法师。居住的都市之外游荡着袭击人类的魔物妖兽，虎视眈眈。崇尚科学的世界变成了崇尚魔法，偏偏有着一样以学渣看待自己的老师，一样目光异样的同学，一样社会底层挣扎的爸爸，一样纯美却不能走路的非血缘妹妹……不过，莫凡发现绝大多数人都只能够主修一系魔法，自己却是全系全能法师！', 'https://www.x23us.com/book/55304', 'https://www.x23us.com/html/55/55304/', 'https://www.x23us.com/files/article/image/55/55304/55304s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:10');
INSERT INTO `book_info` VALUES ('55505', '寒门崛起', '历史军事', '第一千一百三十三章 议和进行中', '朱郎才尽', '2743349字', '2019-07-22', '连载中', null, '13330', '1313308', '85412', '这是一个就业路上屡被蹂躏的古汉语专业研究生，回到了明朝中叶，进入了山村一家幼童身体后的故事。 木讷父亲泼辣娘，一水的极品亲戚，农家小院是非不少。好在，咱有几千年的历史积淀，四书五经八股文，专业也对口，幸又看得到气运。谁言寒门再难出贵子。 国力上升垂拱而治； 法纪松弛，官纪慵散； 有几只奸臣，也闹点倭寇； 但总体上可以说，这是士大夫自由滋生的沃土。 一个寒门崛起的传奇也就从这里生长了。 谨以此文向所有的穿越经典致敬。', 'https://www.x23us.com/book/55505', 'https://www.x23us.com/html/55/55505/', 'https://www.x23us.com/files/article/image/55/55505/55505s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:19');
INSERT INTO `book_info` VALUES ('57036', '真武世界', '都市言情', '后记 （二） 结束也是开始', '蚕茧里的牛', '4778418字', '2019-04-30', '连载中', null, '10623', '1881414', '45671', '有一天，易云去爬山，在山洞中发现了一张神秘的紫色卡片。 他触及卡片的一瞬间，山体塌方。 在他好不容易爬出来之后，却见到了极度不可思议的一幕，呃……具体什么不可思议，请看本书第一章。 ——这是一个瑰丽而又充满未知的真武世界，这是一个平凡少年成就绝世强者的传奇。 作者蚕茧里的牛，已有完本作品《武极天下》，书荒可以看一看。', 'https://www.x23us.com/book/57036', 'https://www.x23us.com/html/57/57036/', 'https://www.x23us.com/files/article/image/57/57036/57036s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:17');
INSERT INTO `book_info` VALUES ('57137', '最强狂兵', '都市言情', '第3855章 像我，又像你！', '烈焰滔滔', '13931751字', '2019-07-23', '连载中', null, '4374', '567275', '24393', '曾经，老子是一代枭傲邪王，现在，老子要做超级护花天王！简单粗暴是我的行事艺术，不服就干是我的生活态度！看顶级狂少如何纵横都市，书写属于他的天王传奇！依旧极爽极热血！（老书《都市邪王》280万字完本，无断更记录，人品保证，放心收藏！书友群： 287999620）', 'https://www.x23us.com/book/57137', 'https://www.x23us.com/html/57/57137/', 'https://www.x23us.com/files/article/image/57/57137/57137s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:13');
INSERT INTO `book_info` VALUES ('57371', '修真聊天群', '都市言情', '第2825章 加入我们的大家庭吧，书航小友！', '圣骑士的传说', '8669436字', '2019-07-23', '连载中', null, '13984', '2822464', '105501', '某天，宋书航意外加入了一个仙侠中二病资深患者的交流群，里面的群友们都以‘道友’相称，群名片都是各种府主、洞主、真人、天师。连群主走失的宠物犬都称为大妖犬离家出走。整天聊的是炼丹、闯秘境、炼功经验啥的。 突然有一天，潜水良久的他突然发现……群里每一个群员，竟然全部是修真者，能移山倒海、长生千年的那种！ 啊啊啊啊，世界观在一夜间彻底崩碎啦！ 书友群：九洲1号群:207572656 九洲2号群:168114177 九洲一号群（vip书友群，需验证）63769632', 'https://www.x23us.com/book/57371', 'https://www.x23us.com/html/57/57371/', 'https://www.x23us.com/files/article/image/57/57371/57371s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:16');
INSERT INTO `book_info` VALUES ('57682', '万古神帝', '都市言情', '第二千三百四十二章 斩浮屠', '飞天鱼', '9031505字', '2019-07-23', '连载中', null, '6972', '2115354', '30088', '八百年前，明帝之子张若尘，被他的未婚妻池瑶公主杀死，一代天骄，就此陨落。八百年后，张若尘重新活了过来，却发现曾经杀死他的未婚妻，已经统一昆仑界，开辟出第一中央帝国，号称“池瑶女皇”。池瑶女皇——统御天下，威临八方；青春永驻，不死不灭。张若尘站在诸皇祠堂外，望着池瑶女皇的神像，心中燃烧起熊熊的仇恨烈焰，“待我重修十三年，敢叫女皇下黄泉”。…………微信公众号开通：feitian玉5，大家可以关注一下。', 'https://www.x23us.com/book/57682', 'https://www.x23us.com/html/57/57682/', 'https://www.x23us.com/files/article/image/57/57682/57682s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:07');
INSERT INTO `book_info` VALUES ('64889', '帝霸', '恐怖灵异', '第3719章无法理解', '厌笔萧生', '12286509字', '2019-07-23', '连载中', null, '6222', '2632675', '31172', '天若逆我，我必封之，神若挡我，我必屠之——站在万族之巅的李七夜立下豪言！ 这是属于一个平凡小子崛起的故事，一个牧童走向万族之巅的征程。 在这里充满神话与奇迹，天魔建起古国，石人筑就天城，鬼族铺成仙路，魅灵修补神府……', 'https://www.x23us.com/book/64889', 'https://www.x23us.com/html/64/64889/', 'https://www.x23us.com/files/article/image/64/64889/64889s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:07');
INSERT INTO `book_info` VALUES ('65525', '最强医圣', '都市言情', '第两千三百三十二章 第二个丹田', '左耳思念', '5829142字', '2019-07-23', '连载中', null, '2954', '240632', '11610', '带着一身通天本领强势回归。会治病、会算命、会炼药、会摆阵、会炼符……“这个世界上没有我办不到的事情！”——沈风。——————————————————读者群：539912693', 'https://www.x23us.com/book/65525', 'https://www.x23us.com/html/65/65525/', 'https://www.x23us.com/files/article/image/65/65525/65525s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:20');
INSERT INTO `book_info` VALUES ('65650', '人皇纪', '其他类型', '第一千九百九十四章 沟通安轧荦山者，死！', '皇甫奇', '6684813字', '2019-07-23', '连载中', null, '2701', '183401', '11526', '我不能把这个世界，让给我所鄙视的人！ 所以，王冲踩着枯骨血海，踏上人皇宝座，挽狂澜于既倒，扶大厦之将倾，成就了一段无上的传说！ 【最新消息，欢迎关注微信公众号：皇甫奇】 qq交流群：427253533　人皇殿', 'https://www.x23us.com/book/65650', 'https://www.x23us.com/html/65/65650/', 'https://www.x23us.com/files/article/image/65/65650/65650s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:12');
INSERT INTO `book_info` VALUES ('66656', '圣墟', '玄幻魔法', '第1412章 跃入上苍', '辰东', '5539348字', '2019-07-23', '连载中', null, '20331', '11341620', '159152', '在破败中崛起，在寂灭中复苏。沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角……', 'https://www.x23us.com/book/66656', 'https://www.x23us.com/html/66/66656/', 'https://www.x23us.com/files/article/image/66/66656/66656s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:02');
INSERT INTO `book_info` VALUES ('67025', '天道图书馆', '玄幻魔法', '第二一五八章 洛七七的消息？', '横扫天涯', '6997523字', '2019-07-23', '连载中', null, '6284', '1893831', '26551', '张悬穿越异界，成了一名光荣的教师，脑海中多出了一个神秘的图书馆。只要他看过的东西，无论人还是物，都能自动形成书籍，记录下对方各种各样的缺点，于是，他牛大了！教学生、收徒弟，开堂授课，调教最强者，传授天下。“灼阳大帝，你怎么不喜欢穿内裤啊？堂堂大帝，能不能注意点形象？”“玲珑仙子，你如果晚上再失眠，可以找我嘛，我这个人唱安眠曲很有一套的！”“还有你，乾坤魔君，能不能少吃点大葱，想把老子熏死吗？”这是一个师道传承，培养、指点世界最强者的牛逼拉风故事。ps1：已有完本《拳皇异界纵横》、《八神庵》、《无尽丹田》等书，质量保证，可入坑！ps2：本书普通群289258448，vip594565673。', 'https://www.x23us.com/book/67025', 'https://www.x23us.com/html/67/67025/', 'https://www.x23us.com/files/article/image/67/67025/67025s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:18');
INSERT INTO `book_info` VALUES ('67926', '一号红人', '其他类型', '_第2904章 反面教材', '山间老寺', '8568183字', '2019-02-22', '连载中', null, '1100', '20155', '1748', '李睿在单位里被美女上司无情欺压，家里面老婆红杏出墙，陷入了人生最低谷。在一次防汛检查时，他跟上司袁晶晶闹翻，事后才知她是市里某领导的儿媳。山洪暴发，李睿凑巧救了某位贵人，自此成为了市里的大红人……', 'https://www.x23us.com/book/67926', 'https://www.x23us.com/html/67/67926/', '/modules/article/images/nocover.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:18');
INSERT INTO `book_info` VALUES ('68255', '牧神记', '玄幻魔法', '第一七五一章 太易渡河', '宅猪', '6149888字', '2019-07-23', '连载中', null, '7412', '85692', '19532', '大墟的祖训说，天黑，别出门。大墟残老村的老弱病残们从江边捡到了一个婴儿，取名秦牧，含辛茹苦将他养大。这一天夜幕降临，黑暗笼罩大墟，秦牧走出了家门……做个春风中荡漾的反派吧！瞎子对他说。秦牧的反派之路，正在崛起！', 'https://www.x23us.com/book/68255', 'https://www.x23us.com/html/68/68255/', '/modules/article/images/nocover.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:06');
INSERT INTO `book_info` VALUES ('69123', '元尊', '玄幻魔法', '第九百三十八章?0?2 法域争斗', '天蚕土豆', '2620542字', '2019-07-23', '连载中', null, '5533', '81968', '28890', '《元尊》 新书 天蚕土豆 元尊此次新书在纵横中文网发布的同时，也会首次在熊猫看书、掌阅、书旗、咪咕阅读、17K、逐浪等阅读平台同步发布新书，让全网用户第一时间读到天蚕土豆的最新小说，共同见证发书时刻。纵横文学携手掌阅科技，阿里文学，咪咕文化，中文在线等行业领先公司，共同为书迷朋友们奉上这场阅读盛宴，让我们一起期待吧！', 'https://www.x23us.com/book/69123', 'https://www.x23us.com/html/69/69123/', 'https://www.x23us.com/files/article/image/69/69123/69123s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:03');
INSERT INTO `book_info` VALUES ('69136', '飞剑问道', '武侠修真', '后记，许多年以后', '我吃西红柿', '2257991字', '2019-05-02', '连载中', null, '6160', '70147', '36797', '在这个世界，有狐仙、河神、水怪、大妖，也有求长生的修行者。 修行者们， 开法眼，可看妖魔鬼怪。 炼一口飞剑，可千里杀敌。 千里眼、顺风耳，更可探查四方。 …… 秦府二公子‘秦云’，便是一位修行者……', 'https://www.x23us.com/book/69136', 'https://www.x23us.com/html/69/69136/', '/modules/article/images/nocover.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:05');
INSERT INTO `book_info` VALUES ('69624', '大道朝天', '玄幻魔法', '第二章不能入宫的理由', '猫腻', '2033800字', '2019-07-23', '连载中', null, '1938', '27335', '1992', '千里杀一人，十步不愿行。（大道朝天官方一群，群号码：311875513，大道朝天官方二群，群号码：220593959，欢迎加入）', 'https://www.x23us.com/book/69624', 'https://www.x23us.com/html/69/69624/', 'https://www.x23us.com/files/article/image/69/69624/69624s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:15');
INSERT INTO `book_info` VALUES ('69628', '大王饶命', '都市言情', '一个讲故事的人', '会说话的肘子', '3062630字', '2019-04-15', '连载中', null, '3257', '39389', '7447', '灵气复苏的时代，寂静生活碎掉了，仿佛雷霆贯穿长空，电光直射天心，雨沙沙地落下。凡逆我们的终将死去，这就是法则。……这是一个吕树依靠毒鸡汤成为大魔王的故事。', 'https://www.x23us.com/book/69628', 'https://www.x23us.com/html/69/69628/', 'https://www.x23us.com/files/article/image/69/69628/69628s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:09');
INSERT INTO `book_info` VALUES ('69629', '汉乡', '历史军事', '第五十九章 汉家儿', '孑与2', '4174748字', '2019-07-23', '连载中', null, '4149', '67611', '19495', '我们接受了祖先的遗产，这让中华辉煌了数千年，我们是如此的心安理得，从未想过要回归那个在刀耕火种中苦苦寻找出路的时代。反哺我们苦难的祖先，并从中找到故乡的真正意义，将是本书要讲的故事。', 'https://www.x23us.com/book/69629', 'https://www.x23us.com/html/69/69629/', 'https://www.x23us.com/files/article/image/69/69629/69629s.jpg', '顶点小说网--https://www.x23us.com', '2019-07-24 00:00:13');

-- ----------------------------
-- Table structure for schedule_task_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_task_job`;
CREATE TABLE `schedule_task_job` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '定时任务主键ID',
  `scheduleTaskName` varchar(30) DEFAULT NULL COMMENT '定时任务名',
  `scheduleTaskNum` int(100) DEFAULT NULL COMMENT '定时任务编号',
  `scheduleTaskCorn` varchar(20) DEFAULT NULL COMMENT '定时任务时间解析',
  `createby` varchar(30) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='定时任务执行时间表';

-- ----------------------------
-- Records of schedule_task_job
-- ----------------------------
INSERT INTO `schedule_task_job` VALUES ('1', '定时爬取小说首页任务', '101', '0 0 0 1/1 * ? ', 'yihur', '2019-06-15 11:05:26', '2019-08-05 11:02:53');
