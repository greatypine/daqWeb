package com.cnpc.pms.personal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "t_store_observe_parameter")
public class ObserveParameter extends DataEntity{
	
	/**
	 * 门店名称 
	 */
	@Column(length = 65,name="store_name",columnDefinition="varchar(65) COMMENT '门店名称'")
	private String store_name;
	
	/**
	 * 门店名称 
	 */
	@Column(length = 65,name="city_name",columnDefinition="varchar(65) COMMENT '城市名称'")
	private String city_name;
	
	/**
	 * 门店id
	 */
	@Column(length = 65,name="store_id",columnDefinition="varchar(65) COMMENT '门店d'")
	private String store_id;
	
	/**
	 * 门店编号
	 */
	@Column(length = 65,name="storeno",columnDefinition="varchar(65) COMMENT '门店编号'")
	private String storeno;
	
	/**
	 * 1.人员
	 */
	
	/**
	 * 员工工装不符合规范
	 */
	@Column(length = 65,name="a1_1",columnDefinition="varchar(65) COMMENT '员工工装不符合规范'")
	private String a1_1;
	
	/**
	 * 员工工装丢失无申报记录(5个工作日内)
	 */
	@Column(length = 65,name="a1_2",columnDefinition="varchar(65) COMMENT '员工工装丢失无申报记录(5个工作日内)'")
	private String a1_2;
	
	/**
	 * 员工胸牌不符合规范
	 */
	@Column(length = 65,name="a2_1",columnDefinition="varchar(65) COMMENT '员工胸牌不符合规范'")
	private String a2_1;
	
	/**
	 * 员工胸牌丢失无申报记录(5个工作日内)
	 */
	@Column(length = 65,name="a2_2",columnDefinition="varchar(65) COMMENT '员工胸牌丢失无申报记录(5个工作日内)'")
	private String a2_2;
	
	/**
	 * 女员工丝巾佩戴不符合规范
	 */
	@Column(length = 65,name="a3_1",columnDefinition="varchar(65) COMMENT '女员工丝巾佩戴不符合规范'")
	private String a3_1;
	
	/**
	 * 女员工丝巾丢失无申报记录(5个工作日内)
	 */
	@Column(length = 65,name="a3_2",columnDefinition="varchar(65) COMMENT '女员工丝巾丢失无申报记录(5个工作日内)'")
	private String a3_2;
	
	/**
	 * 员工个人卫生不符合标准手册要求的
	 */
	@Column(length = 65,name="a4_1",columnDefinition="varchar(65) COMMENT '员工个人卫生不符合标准手册要求的'")
	private String a4_1;
	
	/**
	 * 女员工未化淡妆的（孕妇和哺乳期除外）
	 */
	@Column(length = 65,name="a4_2",columnDefinition="varchar(65) COMMENT '女员工未化淡妆的（孕妇和哺乳期除外）'")
	private String a4_2;
	
	/**
	 * 员工佩戴多余饰品
	 */
	@Column(length = 65,name="a4_3",columnDefinition="varchar(65) COMMENT '员工佩戴多余饰品'")
	private String a4_3;
	
	/**
	 * 无迎宾语
	 */
	@Column(length = 65,name="a5_1",columnDefinition="varchar(65) COMMENT '无迎宾语'")
	private String a5_1;
	
	/**
	 * 无道别语
	 */
	@Column(length = 65,name="a5_2",columnDefinition="varchar(65) COMMENT '无道别语'")
	private String a5_2;
	
	/**
	 * 5秒钟内未及时给客户提供服务
	 */
	@Column(length = 65,name="a5_3",columnDefinition="varchar(65) COMMENT '5秒钟内未及时给客户提供服务'")
	private String a5_3;
	
	/**
	 * 排班表未张贴（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="a6",columnDefinition="varchar(65) COMMENT '排班表未张贴（5月5-18日此项不检查）'")
	private String a6;
	
	/**
	 * 无指纹打卡记录（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="a7_1",columnDefinition="varchar(65) COMMENT '无指纹打卡记录（5月5-18日此项不检查）'")
	private String a7_1;
	
	/**
	 * 指纹打卡记录有缺失（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="a7_2",columnDefinition="varchar(65) COMMENT '指纹打卡记录有缺失（5月5-18日此项不检查）'")
	private String a7_2;
	
	/**
	 * 出勤与排班表不一致（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="a8",columnDefinition="varchar(65) COMMENT '出勤与排班表不一致（5月5-18日此项不检查）'")
	private String a8;
	
	/**
	 * 店长外出未填《出行表》（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="a9",columnDefinition="varchar(65) COMMENT '店长外出未填《出行表》（5月5-18日此项不检查）'")
	private String a9;
	
	/**
	 * 每日工作交接表有缺失
	 */
	@Column(length = 65,name="a10_1",columnDefinition="varchar(65) COMMENT '每日工作交接表有缺失'")
	private String a10_1;
	
	/**
	 * 未使用最新交接表模板
	 */
	@Column(length = 65,name="a10_2",columnDefinition="varchar(65) COMMENT '未使用最新交接表模板'")
	private String a10_2;
	
	/**
	 * 工作交接表填写不完整
	 */
	@Column(length = 65,name="a11",columnDefinition="varchar(65) COMMENT '工作交接表填写不完整'")
	private String a11;
	
	
	
	/**
	 * 2.资产
	 */
	
	/**
	 * 无每月门店资产盘点表
	 */
	@Column(length = 65,name="b1_1",columnDefinition="varchar(65) COMMENT '无每月门店资产盘点表'")
	private String b1_1;
	
	/**
	 * 门店资产盘点表填写不完整
	 */
	@Column(length = 65,name="b1_2",columnDefinition="varchar(65) COMMENT '门店资产盘点表填写不完整'")
	private String b1_2;
	
	/**
	 * 实物与门店资产盘点表不一致
	 */
	@Column(length = 65,name="b2",columnDefinition="varchar(65) COMMENT '实物与门店资产盘点表不一致'")
	private String b2;
	
	/**
	 * 抽查固定资产不能使用且无报修记录(5个工作日内)
	 */
	@Column(length = 65,name="b3",columnDefinition="varchar(65) COMMENT '抽查固定资产不能使用且无报修记录(5个工作日内)'")
	private String b3;
	
	/**
	 * 电动车摆放不整齐（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b4",columnDefinition="varchar(65) COMMENT '电动车摆放不整齐（5月5-18日此项不检查）'")
	private String b4;
	
	/**
	 * 电动车不能使用且未报修(3个工作日内)（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b5_1",columnDefinition="varchar(65) COMMENT '电动车不能使用且未报修(3个工作日内)（5月5-18日此项不检查）'")
	private String b5_1;
	
	/**
	 * 电动车车胎亏气（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b5_2",columnDefinition="varchar(65) COMMENT '电动车车胎亏气（5月5-18日此项不检查）'")
	private String b5_2;
	
	/**
	 * 员工外出未佩戴头盔（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b6_1",columnDefinition="varchar(65) COMMENT '员工外出未佩戴头盔（5月5-18日此项不检查）'")
	private String b6_1;
	
	/**
	 * 员工外出私自搭载（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b6_2",columnDefinition="varchar(65) COMMENT '员工外出私自搭载（5月5-18日此项不检查）'")
	private String b6_2;
	
	/**
	 * 店内有待维修未提报（5月5-18日此项不检查）
	 */
	@Column(length = 65,name="b7",columnDefinition="varchar(65) COMMENT '店内有待维修未提报（5月5-18日此项不检查）'")
	private String b7;
	
	
	/**
	 * 3.财务
	 */

	/**
	 * 保险柜不在监控范围
	 */
	@Column(length = 65,name="c1",columnDefinition="varchar(65) COMMENT '保险柜不在监控范围'")
	private String c1;
	
	/**
	 * C2人员不在场情况下保险柜门未锁
	 */
	@Column(length = 65,name="c2",columnDefinition="varchar(65) COMMENT 'C2人员不在场情况下保险柜门未锁'")
	private String c2;
	
	/**
	 * C3-1现金日报表有缺失
	 */
	@Column(length = 65,name="c3_1",columnDefinition="varchar(65) COMMENT 'C3-1现金日报表有缺失'")
	private String c3_1;
	
	/**
	 * C3-2现金报表填写不正确
	 */
	@Column(length = 65,name="c3_2",columnDefinition="varchar(65) COMMENT 'C3-2现金报表填写不正确'")
	private String c3_2;
	
	/**
	 * C4现金报表未按时上报财务
	 */
	@Column(length = 65,name="c4",columnDefinition="varchar(65) COMMENT 'C4现金报表未按时上报财务'")
	private String c4;
	
	/**
	 * C5现金未分类存放
	 */
	@Column(length = 65,name="c5",columnDefinition="varchar(65) COMMENT 'C5现金未分类存放'")
	private String c5;
	
	/**
	 * C6台账与实际现金不符
	 */
	@Column(length = 65,name="c6",columnDefinition="varchar(65) COMMENT 'C6台账与实际现金不符'")
	private String c6;
	
	/**
	 * C7-1未及时缴存门店营业款现金
	 */
	@Column(length = 65,name="c7_1",columnDefinition="varchar(65) COMMENT 'C7-1未及时缴存门店营业款现金'")
	private String c7_1;
	
	/**
	 * C7-2门店营业款缴存金额与台账不符
	 */
	@Column(length = 65,name="c7_2",columnDefinition="varchar(65) COMMENT 'C7-2门店营业款缴存金额与台账不符'")
	private String c7_2;
	
	/**
	 * C8-1门店入库单未及时录入中台
	 */
	@Column(length = 65,name="c8_1",columnDefinition="varchar(65) COMMENT 'C8-1门店入库单未及时录入中台'")
	private String c8_1;
	
	/**
	 * C8-2门店中台入库数量与入库单不一致
	 */
	@Column(length = 65,name="c8_2",columnDefinition="varchar(65) COMMENT 'C8-2门店中台入库数量与入库单不一致'")
	private String c8_2;
	
	/**
	 * 4.库房
	 */
	
	/**
	 * D1库房门无警示语
	 */
	@Column(length = 65,name="d1",columnDefinition="varchar(65) COMMENT 'D1库房门无警示语'")
	private String d1;
	
	/**
	 * D2-1库房门未关闭
	 */
	@Column(length = 65,name="d2_1",columnDefinition="varchar(65) COMMENT 'D2-1库房门未关闭'")
	private String d2_1;
	
	/**
	 * D2-2独立库房没有专人负责或未锁门
	 */
	@Column(length = 65,name="d2_2",columnDefinition="varchar(65) COMMENT 'D2-2独立库房没有专人负责或未锁门'")
	private String d2_2;
	
	/**
	 * D3-1库房内货品摆放不整齐
	 */
	@Column(length = 65,name="d3_1",columnDefinition="varchar(65) COMMENT 'D3-1库房内货品摆放不整齐'")
	private String d3_1;
	
	/**
	 * D3-2货品无托盘落地摆放
	 */
	@Column(length = 65,name="d3_2",columnDefinition="varchar(65) COMMENT 'D3-2货品无托盘落地摆放'")
	private String d3_2;
	
	/**
	 * D3-3商品未按包装要求摆放（如倒置等）
	 */
	@Column(length = 65,name="d3_3",columnDefinition="varchar(65) COMMENT 'D3-3商品未按包装要求摆放（如倒置等）'")
	private String d3_3;
	
	/**
	 * D3-4货品距离照明灯20cm以内
	 */
	@Column(length = 65,name="d3_4",columnDefinition="varchar(65) COMMENT 'D3-4货品距离照明灯20cm以内'")
	private String d3_4;
	
	/**
	 * D4-1货架未正确标识
	 */
	@Column(length = 65,name="d4_1",columnDefinition="varchar(65) COMMENT 'D4-1货架未正确标识'")
	private String d4_1;
	
	/**
	 * D4-2周转箱未正确标识
	 */
	@Column(length = 65,name="d4_2",columnDefinition="varchar(65) COMMENT 'D4-2周转箱未正确标识'")
	private String d4_2;
	
	/**
	 * D4-3货位未正确标识
	 */
	@Column(length = 65,name="d4_3",columnDefinition="varchar(65) COMMENT 'D4-3货位未正确标识'")
	private String d4_3;
	
	/**
	 * D5-1门店无货品存放台账
	 */
	@Column(length = 65,name="d5_1",columnDefinition="varchar(65) COMMENT 'D5-1门店无货品存放台账'")
	private String d5_1;
	
	/**
	 * D5-2门店货品存放台账填写不完整
	 */
	@Column(length = 65,name="d5_2",columnDefinition="varchar(65) COMMENT 'D5-2门店货品存放台账填写不完整'")
	private String d5_2;
	
	/**
	 * D6SKU实际库存与台账不符
	 */
	@Column(length = 65,name="d6",columnDefinition="varchar(65) COMMENT 'D6SKU实际库存与台账不符'")
	private String d6;
	
	/**
	 * D7-1快递未全部放于库房
	 */
	@Column(length = 65,name="d7_1",columnDefinition="varchar(65) COMMENT 'D7-1快递未全部放于库房'")
	private String d7_1;
	
	/**
	 * D7-2快递临时停放收银台区域堆放凌乱的
	 */
	@Column(length = 65,name="d7_2",columnDefinition="varchar(65) COMMENT 'D7-2快递临时停放收银台区域堆放凌乱的'")
	private String d7_2;
	
	/**
	 * D8寄件、派件未分开存放
	 */
	@Column(length = 65,name="d8",columnDefinition="varchar(65) COMMENT 'D8寄件、派件未分开存放'")
	private String d8;
	
	/**
	 * D9-1库房无摄像头
	 */
	@Column(length = 65,name="d9_1",columnDefinition="varchar(65) COMMENT 'D9-1库房无摄像头'")
	private String d9_1;
	
	/**
	 * D9-2库房摄像头被人旋转照射不合理区域且无报修记录(3个工作日内)
	 */
	@Column(length = 65,name="d9_2",columnDefinition="varchar(65) COMMENT 'D9-2库房摄像头被人旋转照射不合理区域且无报修记录(3个工作日内)'")
	private String d9_2;
	
	/**
	 * D10入库单未按照时间顺序填写
	 */
	@Column(length = 65,name="d10",columnDefinition="varchar(65) COMMENT 'D10入库单未按照时间顺序填写'")
	private String d10;
	
	/**
	 * D11-1库房没有进销存表、出入库表
	 */
	@Column(length = 65,name="d11_1",columnDefinition="varchar(65) COMMENT 'D11-1库房没有进销存表、出入库表'")
	private String d11_1;
	
	/**
	 * D11-2进销存表、出入库表填写不完整
	 */
	@Column(length = 65,name="d11_2",columnDefinition="varchar(65) COMMENT 'D11-2进销存表、出入库表填写不完整'")
	private String d11_2;
	
	/**
	 * D11-3进销存表未每周至少更新一次
	 */
	@Column(length = 65,name="d11_3",columnDefinition="varchar(65) COMMENT 'D11-3进销存表未每周至少更新一次'")
	private String d11_3;
	
	/**
	 * D11-4出入库表未随时更新
	 */
	@Column(length = 65,name="d11_4",columnDefinition="varchar(65) COMMENT 'D11-4出入库表未随时更新'")
	private String d11_4;
	
	/**
	 * D12进销存、出入库表与中台记录有差异且不能说明原因
	 */
	@Column(length = 65,name="d12",columnDefinition="varchar(65) COMMENT 'D12进销存、出入库表与中台记录有差异且不能说明原因'")
	private String d12;
	
	/**
	 * D13-1未遵守小盘、大盘制度
	 */
	@Column(length = 65,name="d13_1",columnDefinition="varchar(65) COMMENT 'D13-1未遵守小盘、大盘制度'")
	private String d13_1;
	
	/**
	 * D13-2盘点表填写不完整
	 */
	@Column(length = 65,name="d13_2",columnDefinition="varchar(65) COMMENT 'D13-2盘点表填写不完整'")
	private String d13_2;
	
	
	/**
	 * 5.能源
	 */
	
	/**
	 * E1-1未使用水电台账
	 */
	@Column(length = 65,name="e1_1",columnDefinition="varchar(65) COMMENT 'E1-1未使用水电台账'")
	private String e1_1;
	
	/**
	 * E1-2水电使用台账记录不完整
	 */
	@Column(length = 65,name="e1_2",columnDefinition="varchar(65) COMMENT 'E1-2水电使用台账记录不完整'")
	private String e1_2;
	
	/**
	 * E2非客户区域无人时灯未关
	 */
	@Column(length = 65,name="e2",columnDefinition="varchar(65) COMMENT 'E2非客户区域无人时灯未关'")
	private String e2;
	
	/**
	 * E3轨道灯没有正确开关
	 */
	@Column(length = 65,name="e3",columnDefinition="varchar(65) COMMENT 'E3轨道灯没有正确开关'")
	private String e3;
	
	/**
	 * E4-1显示屏没有正确开关
	 */
	@Column(length = 65,name="e4_1",columnDefinition="varchar(65) COMMENT 'E4-1显示屏没有正确开关'")
	private String e4_1;
	
	/**
	 * E4-2智能终端设备没有正确开关
	 */
	@Column(length = 65,name="e4_2",columnDefinition="varchar(65) COMMENT 'E4-2智能终端设备没有正确开关'")
	private String e4_2;
	
	/**
	 * E5空调未正确开关
	 */
	@Column(length = 65,name="e5",columnDefinition="varchar(65) COMMENT 'E5空调未正确开关'")
	private String e5;
	
	/**
	 * E6饮水机未正常开启
	 */
	@Column(length = 65,name="e6",columnDefinition="varchar(65) COMMENT 'E6饮水机未正常开启'")
	private String e6;
	
	/**
	 * E7水龙头有跑冒滴漏且未报修
	 */
	@Column(length = 65,name="e7",columnDefinition="varchar(65) COMMENT 'E7水龙头有跑冒滴漏且未报修'")
	private String e7;
	
	/**
	 * E8-1冰柜未及时除霜或有冷凝水外溢
	 */
	@Column(length = 65,name="e8_1",columnDefinition="varchar(65) COMMENT 'E8-1冰柜未及时除霜或有冷凝水外溢'")
	private String e8_1;
	
	/**
	 * E8-2冰柜温度与柜内商品冷藏要求不符
	 */
	@Column(length = 65,name="e8_2",columnDefinition="varchar(65) COMMENT 'E8-2冰柜温度与柜内商品冷藏要求不符'")
	private String e8_2;
	
	/**
	 * E9有非公司用电设备
	 */
	@Column(length = 65,name="e9",columnDefinition="varchar(65) COMMENT 'E9有非公司用电设备'")
	private String e9;
	
	
	/**
	 * 6.环境
	 */
	
	/**
	 * F1垃圾篓内垃圾超过2/3
	 */
	@Column(length = 65,name="f1",columnDefinition="varchar(65) COMMENT 'F1垃圾篓内垃圾超过2/3'")
	private String f1;
	
	/**
	 * F2店内部分区域不干净
	 */
	@Column(length = 65,name="f2",columnDefinition="varchar(65) COMMENT 'F2店内部分区域不干净'")
	private String f2;
	
	/**
	 * F3清洁工具摆放杂乱
	 */
	@Column(length = 65,name="f3",columnDefinition="varchar(65) COMMENT 'F3清洁工具摆放杂乱'")
	private String f3;
	
	/**
	 * F4-1收银台上文件或办公用品摆放不整齐
	 */
	@Column(length = 65,name="f4_1",columnDefinition="varchar(65) COMMENT 'F4-1收银台上文件或办公用品摆放不整齐'")
	private String f4_1;
	
	/**
	 * F4-2在顾客能看到的展示区域粘贴了门店内部管理类表单
	 */
	@Column(length = 65,name="f4_2",columnDefinition="varchar(65) COMMENT 'F4-2在顾客能看到的展示区域粘贴了门店内部管理类表单'")
	private String f4_2;
	
	/**
	 * F4-3收银台上存放私人物品
	 */
	@Column(length = 65,name="f4_3",columnDefinition="varchar(65) COMMENT 'F4-3收银台上存放私人物品'")
	private String f4_3;
	
	/**
	 * F5卫生间内的卷纸、纸巾、洗手液的剩余量无法使用
	 */
	@Column(length = 65,name="f5",columnDefinition="varchar(65) COMMENT 'F5卫生间内的卷纸、纸巾、洗手液的剩余量无法使用'")
	private String f5;
	
	/**
	 * F6空调设置温度不标准（24-28度以外，集中供暖除外）
	 */
	@Column(length = 65,name="f6",columnDefinition="varchar(65) COMMENT 'F6空调设置温度不标准（24-28度以外，集中供暖除外）'")
	private String f6;
	
	/**
	 * F7店内有异味
	 */
	@Column(length = 65,name="f7",columnDefinition="varchar(65) COMMENT 'F7店内有异味'")
	private String f7;
	
	/**
	 * F8-1店内未播放背景音乐
	 */
	@Column(length = 65,name="f8_1",columnDefinition="varchar(65) COMMENT 'F8-1店内未播放背景音乐'")
	private String f8_1;
	
	/**
	 * F8-2背景音乐音量不适中
	 */
	@Column(length = 65,name="f8_2",columnDefinition="varchar(65) COMMENT 'F8-2背景音乐音量不适中'")
	private String f8_2;
	
	/**
	 * F9-1播放短片无音效
	 */
	@Column(length = 65,name="f9_1",columnDefinition="varchar(65) COMMENT 'F9-1播放短片无音效'")
	private String f9_1;
	
	/**
	 * F9-2播放短片音量不标准
	 */
	@Column(length = 65,name="f9_2",columnDefinition="varchar(65) COMMENT 'F9-2播放短片音量不标准'")
	private String f9_2;
	
	
	/**
	 * 7.会议
	 */
	
	/**
	 * G1门店未每天组织召开一次短会
	 */
	@Column(length = 65,name="g1",columnDefinition="varchar(65) COMMENT 'G1门店未每天组织召开一次短会'")
	private String g1;
	
	/**
	 * G2-1缺少培训材料
	 */
	@Column(length = 65,name="g2_1",columnDefinition="varchar(65) COMMENT 'G2-1缺少培训材料'")
	private String g2_1;
	
	/**
	 * G2-2缺少培训签到表
	 */
	@Column(length = 65,name="g2_2",columnDefinition="varchar(65) COMMENT 'G2-2缺少培训签到表'")
	private String g2_2;
	
	/**
	 * G2-3培训签到表缺少员工签字
	 */
	@Column(length = 65,name="g2_3",columnDefinition="varchar(65) COMMENT 'G2-3培训签到表缺少员工签字'")
	private String g2_3;
	
	/**
	 * G3抽查国安大学“日日学”培训内容未答对
	 */
	@Column(length = 65,name="g3",columnDefinition="varchar(65) COMMENT 'G3抽查国安大学“日日学”培训内容未答对'")
	private String g3;
	
	
	/**
	 * 8.安全
	 */
	
	/**
	 * H1-1当班人员手中无钥匙
	 */
	@Column(length = 65,name="h1_1",columnDefinition="varchar(65) COMMENT 'H1-1当班人员手中无钥匙'")
	private String h1_1;
	
	/**
	 * H1-2钥匙打不开门禁
	 */
	@Column(length = 65,name="h1_2",columnDefinition="varchar(65) COMMENT 'H1-2钥匙打不开门禁'")
	private String h1_2;
	
	/**
	 * H2后门无故未关闭
	 */
	@Column(length = 65,name="h2",columnDefinition="varchar(65) COMMENT 'H2后门无故未关闭'")
	private String h2;
	
	/**
	 * H3上墙张贴的健康证已过期
	 */
	@Column(length = 65,name="h3",columnDefinition="varchar(65) COMMENT 'H3上墙张贴的健康证已过期'")
	private String h3;
	
	/**
	 * H4-1过期、损坏、变形商品未独立存放
	 */
	@Column(length = 65,name="h4_1",columnDefinition="varchar(65) COMMENT 'H4-1过期、损坏、变形商品未独立存放'")
	private String h4_1;
	
	/**
	 * H4-2过期、损坏、变形商品独立存放但缺少标识
	 */
	@Column(length = 65,name="h4_2",columnDefinition="varchar(65) COMMENT 'H4-2过期、损坏、变形商品独立存放但缺少标识'")
	private String h4_2;
	
	/**
	 * H4-3陈列了过期变质的水果、蔬菜
	 */
	@Column(length = 65,name="h4_3",columnDefinition="varchar(65) COMMENT 'H4-3陈列了过期变质的水果、蔬菜'")
	private String h4_3;
	
	/**
	 * H5-1食品保质期已过
	 */
	@Column(length = 65,name="h5_1",columnDefinition="varchar(65) COMMENT 'H5-1食品保质期已过'")
	private String h5_1;
	
	/**
	 * H5-2食品缺QS或SC标识（豆米面除外）
	 */
	@Column(length = 65,name="h5_2",columnDefinition="varchar(65) COMMENT 'H5-2食品缺QS或SC标识（豆米面除外）'")
	private String h5_2;
	
	/**
	 * H6灭火器不易拿、被遮挡、堵塞消火栓、防火卷帘门、消防通道及电闸箱等
	 */
	@Column(length = 65,name="h6",columnDefinition="varchar(65) COMMENT 'H6灭火器不易拿、被遮挡、堵塞消火栓、防火卷帘门、消防通道及电闸箱等'")
	private String h6;
	
	/**
	 * H7-1员工不知道灭火器的位置
	 */
	@Column(length = 65,name="h7_1",columnDefinition="varchar(65) COMMENT 'H7-1员工不知道灭火器的位置'")
	private String h7_1;
	
	/**
	 * H7-2员工不会正确使用灭火器
	 */
	@Column(length = 65,name="h7_2",columnDefinition="varchar(65) COMMENT 'H7-2员工不会正确使用灭火器'")
	private String h7_2;
	
	/**
	 * H8灭火器旁堆放杂物
	 */
	@Column(length = 65,name="h8",columnDefinition="varchar(65) COMMENT 'H8灭火器旁堆放杂物'")
	private String h8;
	
	/**
	 * H9-1灭火器质保过期
	 */
	@Column(length = 65,name="h9_1",columnDefinition="varchar(65) COMMENT 'H9-1灭火器质保过期'")
	private String h9_1;
	
	/**
	 * H9-2灭火器压力异常
	 */
	@Column(length = 65,name="h9_2",columnDefinition="varchar(65) COMMENT 'H9-2灭火器压力异常'")
	private String h9_2;
	
	/**
	 * H9-3灭火器保质期标签缺失
	 */
	@Column(length = 65,name="h9_3",columnDefinition="varchar(65) COMMENT 'H9-3灭火器保质期标签缺失'")
	private String h9_3;
	
	/**
	 * H9-4灭火器零部件未正常连接或有破损
	 */
	@Column(length = 65,name="h9_4",columnDefinition="varchar(65) COMMENT 'H9-4灭火器零部件未正常连接或有破损'")
	private String h9_4;
	
	/**
	 * H9-5灭火器不可正常使用且无申报记录(5个工作日内)
	 */
	@Column(length = 65,name="h9_5",columnDefinition="varchar(65) COMMENT 'H9-5灭火器不可正常使用且无申报记录(5个工作日内)'")
	private String h9_5;
	
	/**
	 * H9-6灭火器自检台账未按要求填写
	 */
	@Column(length = 65,name="h9_6",columnDefinition="varchar(65) COMMENT 'H9-6灭火器自检台账未按要求填写'")
	private String h9_6;
	
	/**
	 * H10-1门店无消防预案（纸质版）
	 */
	@Column(length = 65,name="h10_1",columnDefinition="varchar(65) COMMENT 'H10-1门店无消防预案（纸质版）'")
	private String h10_1;
	
	/**
	 * H10-2消防预案（纸质版）未张贴在库房或办公室显著位置
	 */
	@Column(length = 65,name="h10_2",columnDefinition="varchar(65) COMMENT 'H10-2消防预案（纸质版）未张贴在库房或办公室显著位置'")
	private String h10_2;
	
	/**
	 * H11-1插座、插头绝缘体有破损且无报修记录(3个工作日内)
	 */
	@Column(length = 65,name="h11_1",columnDefinition="varchar(65) COMMENT 'H11-1插座、插头绝缘体有破损且无报修记录(3个工作日内)'")
	private String h11_1;
	
	/**
	 * H11-2多个排插串联
	 */
	@Column(length = 65,name="h11_2",columnDefinition="varchar(65) COMMENT 'H11-2多个排插串联'")
	private String h11_2;
	
	
	/**
	 * 9.陈列
	 */
	
	/**
	 * I1-1货品摆放不整齐
	 */
	@Column(length = 65,name="i1_1",columnDefinition="varchar(65) COMMENT 'I1-1货品摆放不整齐'")
	private String i1_1;
	
	/**
	 * I1-2未及时补充货架
	 */
	@Column(length = 65,name="i1_2",columnDefinition="varchar(65) COMMENT 'I1-2未及时补充货架'")
	private String i1_2;
	
	/**
	 * I2商品摆放不符合上小下大原则
	 */
	@Column(length = 65,name="i2",columnDefinition="varchar(65) COMMENT 'I2商品摆放不符合上小下大原则'")
	private String i2;
	
	/**
	 * I3商品陈列不符合分类摆放原则
	 */
	@Column(length = 65,name="i3",columnDefinition="varchar(65) COMMENT 'I3商品陈列不符合分类摆放原则'")
	private String i3;
	
	/**
	 * I4-1海报超过6张
	 */
	@Column(length = 65,name="i4_1",columnDefinition="varchar(65) COMMENT 'I4-1海报超过6张'")
	private String i4_1;
	
	/**
	 * I4-2海报张贴不符合标准，影响门店外观视觉
	 */
	@Column(length = 65,name="i4_2",columnDefinition="varchar(65) COMMENT 'I4-2海报张贴不符合标准，影响门店外观视觉'")
	private String i4_2;
	
	/**
	 * I5-1展架数量超过6个
	 */
	@Column(length = 65,name="i5_1",columnDefinition="varchar(65) COMMENT 'I5-1展架数量超过6个'")
	private String i5_1;
	
	/**
	 * I5-2展架摆放不符合标准，影响门店外观视觉
	 */
	@Column(length = 65,name="i5_2",columnDefinition="varchar(65) COMMENT 'I5-2展架摆放不符合标准，影响门店外观视觉'")
	private String i5_2;
	
	/**
	 * I5-3客户体验区DM单有过期情况
	 */
	@Column(length = 65,name="i5_3",columnDefinition="varchar(65) COMMENT 'I5-3客户体验区DM单有过期情况'")
	private String i5_3;
	
	/**
	 * I6-1业绩版展板未挂在非客户体验区
	 */
	@Column(length = 65,name="i6_1",columnDefinition="varchar(65) COMMENT 'I6-1业绩版展板未挂在非客户体验区'")
	private String i6_1;
	
	/**
	 * I6-2业绩展板内容超过1天未更新或填写不正确
	 */
	@Column(length = 65,name="i6_2",columnDefinition="varchar(65) COMMENT 'I6-2业绩展板内容超过1天未更新或填写不正确'")
	private String i6_2;
	
	/**
	 * I7海报、展架、电子屏等未按北京公司门店点位布置
	 */
	@Column(length = 65,name="i7",columnDefinition="varchar(65) COMMENT 'I7海报、展架、电子屏等未按北京公司门店点位布置'")
	private String i7;
	
	
	/**
	 * 10.档案
	 */
	
	/**
	 * J1门店未下载国安社区门店标准管理手册电子档
	 */
	@Column(length = 65,name="j1",columnDefinition="varchar(65) COMMENT 'J1门店未下载国安社区门店标准管理手册电子档'")
	private String j1;
	
	/**
	 * J2-1各类资料散乱，没有归档
	 */
	@Column(length = 65,name="j2_1",columnDefinition="varchar(65) COMMENT 'J2-1各类资料散乱，没有归档'")
	private String j2_1;
	
	/**
	 * J2-2纸质版文件没有明确标识
	 */
	@Column(length = 65,name="j2_2",columnDefinition="varchar(65) COMMENT 'J2-2纸质版文件没有明确标识'")
	private String j2_2;
	
	/**
	 * J3电子版资料未按规定存档
	 */
	@Column(length = 65,name="j3",columnDefinition="varchar(65) COMMENT 'J3电子版资料未按规定存档'")
	private String j3;
	
	
	/**
	 * 11.产品检查
	 */
	
	/**
	 * K1进口食品无中文标识 
	 */
	@Column(length = 65,name="k1",columnDefinition="varchar(65) COMMENT 'K1进口食品无中文标识'")
	private String k1;
	
	/**
	 * K2.1 门店绿植订单备注中未拍照上传照片
	 */
	@Column(length = 65,name="k2_1",columnDefinition="varchar(65) COMMENT 'K2.1 门店绿植订单备注中未拍照上传照片'")
	private String k2_1;
	
	/**
	 * K2.2门店收衣服检查项目发现的问题未在APP备注
	 */
	@Column(length = 65,name="k2_2",columnDefinition="varchar(65) COMMENT 'K2.2门店收衣服检查项目发现的问题未在APP备注'")
	private String k2_2;
	
	/**
	 * K2.3-1门店保洁订单用户体验报告未上传中台
	 */
	@Column(length = 65,name="k2_3_1",columnDefinition="varchar(65) COMMENT 'K2.3-1门店保洁订单用户体验报告未上传中台'")
	private String k2_3_1;
	
	/**
	 * K2.3-2门店保洁订单中台上显示有用户不满意
	 */
	@Column(length = 65,name="k2_3_2",columnDefinition="varchar(65) COMMENT 'K2.3-2门店保洁订单中台上显示有用户不满意'")
	private String k2_3_2;
	
	/**
	 * K2.4门店维修业务《维修服务单》未上传中台
	 */
	@Column(length = 65,name="k2_4",columnDefinition="varchar(65) COMMENT 'K2.4门店维修业务《维修服务单》未上传中台'")
	private String k2_4;
	
	/**
	 * K2.5洗后净衣回店后超过2天未配送给客人
	 */
	@Column(length = 65,name="k2_5",columnDefinition="varchar(65) COMMENT 'K2.5洗后净衣回店后超过2天未配送给客人'")
	private String k2_5;
	
	/**
	 * K2.6门店有一星期以上的超时未完成订单
	 */
	@Column(length = 65,name="k2_6",columnDefinition="varchar(65) COMMENT 'K2.6门店有一星期以上的超时未完成订单'")
	private String k2_6;
	
	/**
	 * K2.7收银台或明显位置未粘贴“快递禁忌品”、“邮政寄递出示身份证”图片
	 */
	@Column(length = 65,name="k2_7",columnDefinition="varchar(65) COMMENT 'K2.7收银台或明显位置未粘贴“快递禁忌品”、“邮政寄递出示身份证”图片'")
	private String k2_7;
	
	/**
	 * K2.8门店揽收快件外包装上没有加盖国安社区快递验视章
	 */
	@Column(length = 65,name="k2_8",columnDefinition="varchar(65) COMMENT 'K2.8门店揽收快件外包装上没有加盖国安社区快递验视章'")
	private String k2_8;
	
	/**
	 * K2.9门店揽收快件外包装已被封口、无法二次验视
	 */
	@Column(length = 65,name="k2_9",columnDefinition="varchar(65) COMMENT 'K2.9门店揽收快件外包装已被封口、无法二次验视'")
	private String k2_9;
	
	/**
	 * K3门店划片数量不正确
	 */
	@Column(length = 65,name="k3",columnDefinition="varchar(65) COMMENT 'K3门店划片数量不正确'")
	private String k3;
	
	
	/**
	 * 12.518专项检查
	 */
	
	/**
	 * L1、门店518联盟商家未落实宣传物料的摆放（仅限5.5-18检查）
	 */
	@Column(length = 65,name="l1",columnDefinition="varchar(65) COMMENT 'L1、门店518联盟商家未落实宣传物料的摆放（仅限5.5-18检查）'")
	private String l1;
	
	/**
	 * L2、门店未按计划开展地推（仅限5.5-18检查）
	 */
	@Column(length = 65,name="l2",columnDefinition="varchar(65) COMMENT 'L2、门店未按计划开展地推（仅限5.5-18检查）'")
	private String l2;
	
	/**
	 * L3-1门店地推点员工仪容仪表不符合规范（仅限5.5-18检查）
	 */
	@Column(length = 65,name="l3_1",columnDefinition="varchar(65) COMMENT 'L3-1门店地推点员工仪容仪表不符合规范（仅限5.5-18检查）'")
	private String l3_1;
	
	/**
	 * L3-2门店地推点未按要求摆放宣传物料（仅限5.5-18检查）
	 */
	@Column(length = 65,name="l3_2",columnDefinition="varchar(65) COMMENT 'L3-2门店地推点未按要求摆放宣传物料（仅限5.5-18检查）'")
	private String l3_2;
	
	/**
	 * 表格相关信息
	 */
	
	/**
	 * 扣分合计
	 */
	@Column(length = 65,name="buckle_points_combined",columnDefinition="varchar(65) COMMENT '扣分合计'")
	private String buckle_points_combined;
	
	/**
	 * 本次明察得分
	 */
	@Column(length = 65,name="points_combined",columnDefinition="varchar(65) COMMENT '本次明察得分'")
	private String points_combined;
	
	/**
	 * 进店日期
	 */
	@Column(length = 65,name="observe_date",columnDefinition="varchar(65) COMMENT '进店日期'")
	private String observe_date;
	
	/**
	 * 检查人
	 */
	@Column(length = 65,name="observe_person",columnDefinition="varchar(65) COMMENT '检查人'")
	private String observe_person;
	
	/**
	 * 检查年月
	 */
	@Column(length = 65,name="observe_month",columnDefinition="varchar(65) COMMENT '检查月份'")
	private String observe_month;
	
	/**
	 * 分属类型： 0.扣分   1.扣分情况说明   2.被扣分员工姓名，工号口分数
	 */
	@Column(length = 65,name="type",columnDefinition="varchar(65) COMMENT '分属类型： 0.扣分   1.扣分情况说明   2.被扣分员工姓名，工号口分数'")
	private String type;
	
	/**
	 * 明查问题数量
	 */
	@Column(length = 65,name="observe_question_number",columnDefinition="varchar(65) COMMENT '分属类型： 0.扣分   1.扣分情况说明   2.被扣分员工姓名，工号口分数'")
	private String observe_question_number;
	

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getStoreno() {
		return storeno;
	}

	public void setStoreno(String storeno) {
		this.storeno = storeno;
	}

	public String getA1_1() {
		return a1_1;
	}

	public void setA1_1(String a1_1) {
		this.a1_1 = a1_1;
	}

	public String getA1_2() {
		return a1_2;
	}

	public void setA1_2(String a1_2) {
		this.a1_2 = a1_2;
	}

	public String getA2_1() {
		return a2_1;
	}

	public void setA2_1(String a2_1) {
		this.a2_1 = a2_1;
	}

	public String getA2_2() {
		return a2_2;
	}

	public void setA2_2(String a2_2) {
		this.a2_2 = a2_2;
	}

	public String getA3_1() {
		return a3_1;
	}

	public void setA3_1(String a3_1) {
		this.a3_1 = a3_1;
	}

	public String getA3_2() {
		return a3_2;
	}

	public void setA3_2(String a3_2) {
		this.a3_2 = a3_2;
	}

	public String getA4_1() {
		return a4_1;
	}

	public void setA4_1(String a4_1) {
		this.a4_1 = a4_1;
	}

	public String getA4_2() {
		return a4_2;
	}

	public void setA4_2(String a4_2) {
		this.a4_2 = a4_2;
	}

	public String getA4_3() {
		return a4_3;
	}

	public void setA4_3(String a4_3) {
		this.a4_3 = a4_3;
	}

	public String getA5_1() {
		return a5_1;
	}

	public void setA5_1(String a5_1) {
		this.a5_1 = a5_1;
	}

	public String getA5_2() {
		return a5_2;
	}

	public void setA5_2(String a5_2) {
		this.a5_2 = a5_2;
	}

	public String getA5_3() {
		return a5_3;
	}

	public void setA5_3(String a5_3) {
		this.a5_3 = a5_3;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7_1() {
		return a7_1;
	}

	public void setA7_1(String a7_1) {
		this.a7_1 = a7_1;
	}

	public String getA7_2() {
		return a7_2;
	}

	public void setA7_2(String a7_2) {
		this.a7_2 = a7_2;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10_1() {
		return a10_1;
	}

	public void setA10_1(String a10_1) {
		this.a10_1 = a10_1;
	}

	public String getA10_2() {
		return a10_2;
	}

	public void setA10_2(String a10_2) {
		this.a10_2 = a10_2;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getB1_1() {
		return b1_1;
	}

	public void setB1_1(String b1_1) {
		this.b1_1 = b1_1;
	}

	public String getB1_2() {
		return b1_2;
	}

	public void setB1_2(String b1_2) {
		this.b1_2 = b1_2;
	}

	public String getB2() {
		return b2;
	}

	public void setB2(String b2) {
		this.b2 = b2;
	}

	public String getB3() {
		return b3;
	}

	public void setB3(String b3) {
		this.b3 = b3;
	}

	public String getB4() {
		return b4;
	}

	public void setB4(String b4) {
		this.b4 = b4;
	}

	public String getB5_1() {
		return b5_1;
	}

	public void setB5_1(String b5_1) {
		this.b5_1 = b5_1;
	}

	public String getB5_2() {
		return b5_2;
	}

	public void setB5_2(String b5_2) {
		this.b5_2 = b5_2;
	}

	public String getB6_1() {
		return b6_1;
	}

	public void setB6_1(String b6_1) {
		this.b6_1 = b6_1;
	}

	public String getB6_2() {
		return b6_2;
	}

	public void setB6_2(String b6_2) {
		this.b6_2 = b6_2;
	}

	public String getB7() {
		return b7;
	}

	public void setB7(String b7) {
		this.b7 = b7;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getC3_1() {
		return c3_1;
	}

	public void setC3_1(String c3_1) {
		this.c3_1 = c3_1;
	}

	public String getC3_2() {
		return c3_2;
	}

	public void setC3_2(String c3_2) {
		this.c3_2 = c3_2;
	}

	public String getC4() {
		return c4;
	}

	public void setC4(String c4) {
		this.c4 = c4;
	}

	public String getC5() {
		return c5;
	}

	public void setC5(String c5) {
		this.c5 = c5;
	}

	public String getC6() {
		return c6;
	}

	public void setC6(String c6) {
		this.c6 = c6;
	}

	public String getC7_1() {
		return c7_1;
	}

	public void setC7_1(String c7_1) {
		this.c7_1 = c7_1;
	}

	public String getC7_2() {
		return c7_2;
	}

	public void setC7_2(String c7_2) {
		this.c7_2 = c7_2;
	}

	public String getC8_1() {
		return c8_1;
	}

	public void setC8_1(String c8_1) {
		this.c8_1 = c8_1;
	}

	public String getC8_2() {
		return c8_2;
	}

	public void setC8_2(String c8_2) {
		this.c8_2 = c8_2;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2_1() {
		return d2_1;
	}

	public void setD2_1(String d2_1) {
		this.d2_1 = d2_1;
	}

	public String getD2_2() {
		return d2_2;
	}

	public void setD2_2(String d2_2) {
		this.d2_2 = d2_2;
	}

	public String getD3_1() {
		return d3_1;
	}

	public void setD3_1(String d3_1) {
		this.d3_1 = d3_1;
	}

	public String getD3_2() {
		return d3_2;
	}

	public void setD3_2(String d3_2) {
		this.d3_2 = d3_2;
	}

	public String getD3_3() {
		return d3_3;
	}

	public void setD3_3(String d3_3) {
		this.d3_3 = d3_3;
	}

	public String getD3_4() {
		return d3_4;
	}

	public void setD3_4(String d3_4) {
		this.d3_4 = d3_4;
	}

	public String getD4_1() {
		return d4_1;
	}

	public void setD4_1(String d4_1) {
		this.d4_1 = d4_1;
	}

	public String getD4_2() {
		return d4_2;
	}

	public void setD4_2(String d4_2) {
		this.d4_2 = d4_2;
	}

	public String getD4_3() {
		return d4_3;
	}

	public void setD4_3(String d4_3) {
		this.d4_3 = d4_3;
	}

	public String getD5_1() {
		return d5_1;
	}

	public void setD5_1(String d5_1) {
		this.d5_1 = d5_1;
	}

	public String getD5_2() {
		return d5_2;
	}

	public void setD5_2(String d5_2) {
		this.d5_2 = d5_2;
	}

	public String getD6() {
		return d6;
	}

	public void setD6(String d6) {
		this.d6 = d6;
	}

	public String getD7_1() {
		return d7_1;
	}

	public void setD7_1(String d7_1) {
		this.d7_1 = d7_1;
	}

	public String getD7_2() {
		return d7_2;
	}

	public void setD7_2(String d7_2) {
		this.d7_2 = d7_2;
	}

	public String getD8() {
		return d8;
	}

	public void setD8(String d8) {
		this.d8 = d8;
	}

	public String getD9_1() {
		return d9_1;
	}

	public void setD9_1(String d9_1) {
		this.d9_1 = d9_1;
	}

	public String getD9_2() {
		return d9_2;
	}

	public void setD9_2(String d9_2) {
		this.d9_2 = d9_2;
	}

	public String getD10() {
		return d10;
	}

	public void setD10(String d10) {
		this.d10 = d10;
	}

	public String getD11_1() {
		return d11_1;
	}

	public void setD11_1(String d11_1) {
		this.d11_1 = d11_1;
	}

	public String getD11_2() {
		return d11_2;
	}

	public void setD11_2(String d11_2) {
		this.d11_2 = d11_2;
	}

	public String getD11_3() {
		return d11_3;
	}

	public void setD11_3(String d11_3) {
		this.d11_3 = d11_3;
	}

	public String getD11_4() {
		return d11_4;
	}

	public void setD11_4(String d11_4) {
		this.d11_4 = d11_4;
	}

	public String getD12() {
		return d12;
	}

	public void setD12(String d12) {
		this.d12 = d12;
	}

	public String getD13_1() {
		return d13_1;
	}

	public void setD13_1(String d13_1) {
		this.d13_1 = d13_1;
	}

	public String getD13_2() {
		return d13_2;
	}

	public void setD13_2(String d13_2) {
		this.d13_2 = d13_2;
	}

	public String getE1_1() {
		return e1_1;
	}

	public void setE1_1(String e1_1) {
		this.e1_1 = e1_1;
	}

	public String getE1_2() {
		return e1_2;
	}

	public void setE1_2(String e1_2) {
		this.e1_2 = e1_2;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public String getE3() {
		return e3;
	}

	public void setE3(String e3) {
		this.e3 = e3;
	}

	public String getE4_1() {
		return e4_1;
	}

	public void setE4_1(String e4_1) {
		this.e4_1 = e4_1;
	}

	public String getE4_2() {
		return e4_2;
	}

	public void setE4_2(String e4_2) {
		this.e4_2 = e4_2;
	}

	public String getE5() {
		return e5;
	}

	public void setE5(String e5) {
		this.e5 = e5;
	}

	public String getE6() {
		return e6;
	}

	public void setE6(String e6) {
		this.e6 = e6;
	}

	public String getE7() {
		return e7;
	}

	public void setE7(String e7) {
		this.e7 = e7;
	}

	public String getE8_1() {
		return e8_1;
	}

	public void setE8_1(String e8_1) {
		this.e8_1 = e8_1;
	}

	public String getE8_2() {
		return e8_2;
	}

	public void setE8_2(String e8_2) {
		this.e8_2 = e8_2;
	}

	public String getE9() {
		return e9;
	}

	public void setE9(String e9) {
		this.e9 = e9;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public String getF4_1() {
		return f4_1;
	}

	public void setF4_1(String f4_1) {
		this.f4_1 = f4_1;
	}

	public String getF4_2() {
		return f4_2;
	}

	public void setF4_2(String f4_2) {
		this.f4_2 = f4_2;
	}

	public String getF4_3() {
		return f4_3;
	}

	public void setF4_3(String f4_3) {
		this.f4_3 = f4_3;
	}

	public String getF5() {
		return f5;
	}

	public void setF5(String f5) {
		this.f5 = f5;
	}

	public String getF6() {
		return f6;
	}

	public void setF6(String f6) {
		this.f6 = f6;
	}

	public String getF7() {
		return f7;
	}

	public void setF7(String f7) {
		this.f7 = f7;
	}

	public String getF8_1() {
		return f8_1;
	}

	public void setF8_1(String f8_1) {
		this.f8_1 = f8_1;
	}

	public String getF8_2() {
		return f8_2;
	}

	public void setF8_2(String f8_2) {
		this.f8_2 = f8_2;
	}

	public String getF9_1() {
		return f9_1;
	}

	public void setF9_1(String f9_1) {
		this.f9_1 = f9_1;
	}

	public String getF9_2() {
		return f9_2;
	}

	public void setF9_2(String f9_2) {
		this.f9_2 = f9_2;
	}

	public String getG1() {
		return g1;
	}

	public void setG1(String g1) {
		this.g1 = g1;
	}

	public String getG2_1() {
		return g2_1;
	}

	public void setG2_1(String g2_1) {
		this.g2_1 = g2_1;
	}

	public String getG2_2() {
		return g2_2;
	}

	public void setG2_2(String g2_2) {
		this.g2_2 = g2_2;
	}

	public String getG2_3() {
		return g2_3;
	}

	public void setG2_3(String g2_3) {
		this.g2_3 = g2_3;
	}

	public String getG3() {
		return g3;
	}

	public void setG3(String g3) {
		this.g3 = g3;
	}

	public String getH1_1() {
		return h1_1;
	}

	public void setH1_1(String h1_1) {
		this.h1_1 = h1_1;
	}

	public String getH1_2() {
		return h1_2;
	}

	public void setH1_2(String h1_2) {
		this.h1_2 = h1_2;
	}

	public String getH2() {
		return h2;
	}

	public void setH2(String h2) {
		this.h2 = h2;
	}

	public String getH3() {
		return h3;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getH4_1() {
		return h4_1;
	}

	public void setH4_1(String h4_1) {
		this.h4_1 = h4_1;
	}

	public String getH4_2() {
		return h4_2;
	}

	public void setH4_2(String h4_2) {
		this.h4_2 = h4_2;
	}

	public String getH4_3() {
		return h4_3;
	}

	public void setH4_3(String h4_3) {
		this.h4_3 = h4_3;
	}

	public String getH5_1() {
		return h5_1;
	}

	public void setH5_1(String h5_1) {
		this.h5_1 = h5_1;
	}

	public String getH5_2() {
		return h5_2;
	}

	public void setH5_2(String h5_2) {
		this.h5_2 = h5_2;
	}

	public String getH6() {
		return h6;
	}

	public void setH6(String h6) {
		this.h6 = h6;
	}

	public String getH7_1() {
		return h7_1;
	}

	public void setH7_1(String h7_1) {
		this.h7_1 = h7_1;
	}

	public String getH7_2() {
		return h7_2;
	}

	public void setH7_2(String h7_2) {
		this.h7_2 = h7_2;
	}

	public String getH8() {
		return h8;
	}

	public void setH8(String h8) {
		this.h8 = h8;
	}

	public String getH9_1() {
		return h9_1;
	}

	public void setH9_1(String h9_1) {
		this.h9_1 = h9_1;
	}

	public String getH9_2() {
		return h9_2;
	}

	public void setH9_2(String h9_2) {
		this.h9_2 = h9_2;
	}

	public String getH9_3() {
		return h9_3;
	}

	public void setH9_3(String h9_3) {
		this.h9_3 = h9_3;
	}

	public String getH9_4() {
		return h9_4;
	}

	public void setH9_4(String h9_4) {
		this.h9_4 = h9_4;
	}

	public String getH9_5() {
		return h9_5;
	}

	public void setH9_5(String h9_5) {
		this.h9_5 = h9_5;
	}

	public String getH9_6() {
		return h9_6;
	}

	public void setH9_6(String h9_6) {
		this.h9_6 = h9_6;
	}

	public String getH10_1() {
		return h10_1;
	}

	public void setH10_1(String h10_1) {
		this.h10_1 = h10_1;
	}

	public String getH10_2() {
		return h10_2;
	}

	public void setH10_2(String h10_2) {
		this.h10_2 = h10_2;
	}

	public String getH11_1() {
		return h11_1;
	}

	public void setH11_1(String h11_1) {
		this.h11_1 = h11_1;
	}

	public String getH11_2() {
		return h11_2;
	}

	public void setH11_2(String h11_2) {
		this.h11_2 = h11_2;
	}

	public String getI1_1() {
		return i1_1;
	}

	public void setI1_1(String i1_1) {
		this.i1_1 = i1_1;
	}

	public String getI1_2() {
		return i1_2;
	}

	public void setI1_2(String i1_2) {
		this.i1_2 = i1_2;
	}

	public String getI2() {
		return i2;
	}

	public void setI2(String i2) {
		this.i2 = i2;
	}

	public String getI3() {
		return i3;
	}

	public void setI3(String i3) {
		this.i3 = i3;
	}

	public String getI4_1() {
		return i4_1;
	}

	public void setI4_1(String i4_1) {
		this.i4_1 = i4_1;
	}

	public String getI4_2() {
		return i4_2;
	}

	public void setI4_2(String i4_2) {
		this.i4_2 = i4_2;
	}

	public String getI5_1() {
		return i5_1;
	}

	public void setI5_1(String i5_1) {
		this.i5_1 = i5_1;
	}

	public String getI5_2() {
		return i5_2;
	}

	public void setI5_2(String i5_2) {
		this.i5_2 = i5_2;
	}

	public String getI5_3() {
		return i5_3;
	}

	public void setI5_3(String i5_3) {
		this.i5_3 = i5_3;
	}

	public String getI6_1() {
		return i6_1;
	}

	public void setI6_1(String i6_1) {
		this.i6_1 = i6_1;
	}

	public String getI6_2() {
		return i6_2;
	}

	public void setI6_2(String i6_2) {
		this.i6_2 = i6_2;
	}

	public String getI7() {
		return i7;
	}

	public void setI7(String i7) {
		this.i7 = i7;
	}

	public String getJ1() {
		return j1;
	}

	public void setJ1(String j1) {
		this.j1 = j1;
	}

	public String getJ2_1() {
		return j2_1;
	}

	public void setJ2_1(String j2_1) {
		this.j2_1 = j2_1;
	}

	public String getJ2_2() {
		return j2_2;
	}

	public void setJ2_2(String j2_2) {
		this.j2_2 = j2_2;
	}

	public String getJ3() {
		return j3;
	}

	public void setJ3(String j3) {
		this.j3 = j3;
	}

	public String getK1() {
		return k1;
	}

	public void setK1(String k1) {
		this.k1 = k1;
	}

	public String getK2_1() {
		return k2_1;
	}

	public void setK2_1(String k2_1) {
		this.k2_1 = k2_1;
	}

	public String getK2_2() {
		return k2_2;
	}

	public void setK2_2(String k2_2) {
		this.k2_2 = k2_2;
	}

	public String getK2_3_1() {
		return k2_3_1;
	}

	public void setK2_3_1(String k2_3_1) {
		this.k2_3_1 = k2_3_1;
	}

	public String getK2_3_2() {
		return k2_3_2;
	}

	public void setK2_3_2(String k2_3_2) {
		this.k2_3_2 = k2_3_2;
	}

	public String getK2_4() {
		return k2_4;
	}

	public void setK2_4(String k2_4) {
		this.k2_4 = k2_4;
	}

	public String getK2_5() {
		return k2_5;
	}

	public void setK2_5(String k2_5) {
		this.k2_5 = k2_5;
	}

	public String getK2_6() {
		return k2_6;
	}

	public void setK2_6(String k2_6) {
		this.k2_6 = k2_6;
	}

	public String getK2_7() {
		return k2_7;
	}

	public void setK2_7(String k2_7) {
		this.k2_7 = k2_7;
	}

	public String getK2_8() {
		return k2_8;
	}

	public void setK2_8(String k2_8) {
		this.k2_8 = k2_8;
	}

	public String getK2_9() {
		return k2_9;
	}

	public void setK2_9(String k2_9) {
		this.k2_9 = k2_9;
	}

	public String getK3() {
		return k3;
	}

	public void setK3(String k3) {
		this.k3 = k3;
	}

	public String getL1() {
		return l1;
	}

	public void setL1(String l1) {
		this.l1 = l1;
	}

	public String getL2() {
		return l2;
	}

	public void setL2(String l2) {
		this.l2 = l2;
	}

	public String getL3_1() {
		return l3_1;
	}

	public void setL3_1(String l3_1) {
		this.l3_1 = l3_1;
	}

	public String getL3_2() {
		return l3_2;
	}

	public void setL3_2(String l3_2) {
		this.l3_2 = l3_2;
	}

	public String getBuckle_points_combined() {
		return buckle_points_combined;
	}

	public void setBuckle_points_combined(String buckle_points_combined) {
		this.buckle_points_combined = buckle_points_combined;
	}

	public String getPoints_combined() {
		return points_combined;
	}

	public void setPoints_combined(String points_combined) {
		this.points_combined = points_combined;
	}

	public String getObserve_date() {
		return observe_date;
	}

	public void setObserve_date(String observe_date) {
		this.observe_date = observe_date;
	}

	public String getObserve_person() {
		return observe_person;
	}

	public void setObserve_person(String observe_person) {
		this.observe_person = observe_person;
	}

	public String getObserve_month() {
		return observe_month;
	}

	public void setObserve_month(String observe_month) {
		this.observe_month = observe_month;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObserve_question_number() {
		return observe_question_number;
	}

	public void setObserve_question_number(String observe_question_number) {
		this.observe_question_number = observe_question_number;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	
	
}

