package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "df_target_entry")
public class TargetEntry extends DataEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 32, name = "store_code")
	private String store_code;// 门店id

	@Column(length = 250, name = "store_name")
	private String store_name;// 门店名称

	@Column(length = 36, name = "businessGroup_code")
	private String businessGroup_code;// 事业群id

	@Column(length = 250, name = "businessGroup_name")
	private String businessGroup_name;// 事业群名称
	/**
	 * 毛利
	 */
	@Column(name = "maori_target")
	private BigDecimal maori_target;
	/**
	 * 利润
	 */
	@Column(name = "profit_target")
	private BigDecimal profit_target;
	/**
	 * 用户
	 */
	@Column(name = "user_target")
	private BigDecimal user_target;

	/**
	 * 城市名称
	 */
	@Column(length = 45, name = "channel_name")
	private String channel_name;
	/**
	 * 城市编号
	 */
	@Column(length = 45, name = "channel_code")
	private String channel_code;

	@Column(length = 45,name = "frame_time")
	private String frame_time;

	@Column(length = 45, name = "user_code")
	private String user_code;

	@Column(name = "url", length = 200)
	private String url;

	@Transient
	private String caozuo;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getBusinessGroup_code() {
		return businessGroup_code;
	}

	public void setBusinessGroup_code(String businessGroup_code) {
		this.businessGroup_code = businessGroup_code;
	}

	public String getBusinessGroup_name() {
		return businessGroup_name;
	}

	public void setBusinessGroup_name(String businessGroup_name) {
		this.businessGroup_name = businessGroup_name;
	}

	public BigDecimal getMaori_target() {
		return maori_target;
	}

	public void setMaori_target(BigDecimal maori_target) {
		this.maori_target = maori_target;
	}

	public BigDecimal getProfit_target() {
		return profit_target;
	}

	public void setProfit_target(BigDecimal profit_target) {
		this.profit_target = profit_target;
	}

	public BigDecimal getUser_target() {
		return user_target;
	}

	public void setUser_target(BigDecimal user_target) {
		this.user_target = user_target;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getFrame_time() {
		return frame_time;
	}

	public void setFrame_time(String frame_time) {
		this.frame_time = frame_time;
	}

	public String getCaozuo() {
		return caozuo;
	}

	public void setCaozuo(String caozuo) {
		this.caozuo = caozuo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
