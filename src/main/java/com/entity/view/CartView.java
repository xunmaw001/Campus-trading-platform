package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.CartEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 购物车
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("cart")
public class CartView extends CartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表

	//级联表 商品
					 
		/**
		* 商品 的 用户
		*/
		@ColumnInfo(comment="用户",type="int(11)")
		private Integer shangpinYonghuId;
		/**
		* 商品名称
		*/

		@ColumnInfo(comment="商品名称",type="varchar(200)")
		private String shangpinName;
		/**
		* 商品照片
		*/

		@ColumnInfo(comment="商品照片",type="varchar(200)")
		private String shangpinPhoto;
		/**
		* 商品类型
		*/
		@ColumnInfo(comment="商品类型",type="int(11)")
		private Integer shangpinTypes;
			/**
			* 商品类型的值
			*/
			@ColumnInfo(comment="商品类型的字典表值",type="varchar(200)")
			private String shangpinValue;
		/**
		* 商品库存
		*/

		@ColumnInfo(comment="商品库存",type="int(11)")
		private Integer shangpinKucunNumber;
		/**
		* 原价
		*/
		@ColumnInfo(comment="原价",type="decimal(10,2)")
		private Double shangpinOldMoney;
		/**
		* 现价
		*/
		@ColumnInfo(comment="现价",type="decimal(10,2)")
		private Double shangpinNewMoney;
		/**
		* 点击次数
		*/

		@ColumnInfo(comment="点击次数",type="int(11)")
		private Integer shangpinClicknum;
		/**
		* 商品审核
		*/
		@ColumnInfo(comment="商品审核",type="int(11)")
		private Integer shangpinYesnoTypes;
			/**
			* 商品审核的值
			*/
			@ColumnInfo(comment="商品审核的字典表值",type="varchar(200)")
			private String shangpinYesnoValue;
		/**
		* 审核结果
		*/

		@ColumnInfo(comment="审核结果",type="text")
		private String shangpinYesnoText;
		/**
		* 是否上架
		*/
		@ColumnInfo(comment="是否上架",type="int(11)")
		private Integer shangxiaTypes;
			/**
			* 是否上架的值
			*/
			@ColumnInfo(comment="是否上架的字典表值",type="varchar(200)")
			private String shangxiaValue;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer shangpinDelete;
		/**
		* 商品简介
		*/

		@ColumnInfo(comment="商品简介",type="text")
		private String shangpinContent;
	//级联表 用户
		/**
		* 用户姓名
		*/

		@ColumnInfo(comment="用户姓名",type="varchar(200)")
		private String yonghuName;
		/**
		* 联系方式
		*/

		@ColumnInfo(comment="联系方式",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 身份证号
		*/

		@ColumnInfo(comment="身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 用户头像
		*/

		@ColumnInfo(comment="用户头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 电子邮箱
		*/

		@ColumnInfo(comment="电子邮箱",type="varchar(200)")
		private String yonghuEmail;
		/**
		* 余额
		*/
		@ColumnInfo(comment="余额",type="decimal(10,2)")
		private Double newMoney;



	public CartView() {

	}

	public CartView(CartEntity cartEntity) {
		try {
			BeanUtils.copyProperties(this, cartEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	//级联表的get和set 商品
		/**
		* 获取：商品 的 用户
		*/
		public Integer getShangpinYonghuId() {
			return shangpinYonghuId;
		}
		/**
		* 设置：商品 的 用户
		*/
		public void setShangpinYonghuId(Integer shangpinYonghuId) {
			this.shangpinYonghuId = shangpinYonghuId;
		}

		/**
		* 获取： 商品名称
		*/
		public String getShangpinName() {
			return shangpinName;
		}
		/**
		* 设置： 商品名称
		*/
		public void setShangpinName(String shangpinName) {
			this.shangpinName = shangpinName;
		}

		/**
		* 获取： 商品照片
		*/
		public String getShangpinPhoto() {
			return shangpinPhoto;
		}
		/**
		* 设置： 商品照片
		*/
		public void setShangpinPhoto(String shangpinPhoto) {
			this.shangpinPhoto = shangpinPhoto;
		}
		/**
		* 获取： 商品类型
		*/
		public Integer getShangpinTypes() {
			return shangpinTypes;
		}
		/**
		* 设置： 商品类型
		*/
		public void setShangpinTypes(Integer shangpinTypes) {
			this.shangpinTypes = shangpinTypes;
		}


			/**
			* 获取： 商品类型的值
			*/
			public String getShangpinValue() {
				return shangpinValue;
			}
			/**
			* 设置： 商品类型的值
			*/
			public void setShangpinValue(String shangpinValue) {
				this.shangpinValue = shangpinValue;
			}

		/**
		* 获取： 商品库存
		*/
		public Integer getShangpinKucunNumber() {
			return shangpinKucunNumber;
		}
		/**
		* 设置： 商品库存
		*/
		public void setShangpinKucunNumber(Integer shangpinKucunNumber) {
			this.shangpinKucunNumber = shangpinKucunNumber;
		}

		/**
		* 获取： 原价
		*/
		public Double getShangpinOldMoney() {
			return shangpinOldMoney;
		}
		/**
		* 设置： 原价
		*/
		public void setShangpinOldMoney(Double shangpinOldMoney) {
			this.shangpinOldMoney = shangpinOldMoney;
		}

		/**
		* 获取： 现价
		*/
		public Double getShangpinNewMoney() {
			return shangpinNewMoney;
		}
		/**
		* 设置： 现价
		*/
		public void setShangpinNewMoney(Double shangpinNewMoney) {
			this.shangpinNewMoney = shangpinNewMoney;
		}

		/**
		* 获取： 点击次数
		*/
		public Integer getShangpinClicknum() {
			return shangpinClicknum;
		}
		/**
		* 设置： 点击次数
		*/
		public void setShangpinClicknum(Integer shangpinClicknum) {
			this.shangpinClicknum = shangpinClicknum;
		}
		/**
		* 获取： 商品审核
		*/
		public Integer getShangpinYesnoTypes() {
			return shangpinYesnoTypes;
		}
		/**
		* 设置： 商品审核
		*/
		public void setShangpinYesnoTypes(Integer shangpinYesnoTypes) {
			this.shangpinYesnoTypes = shangpinYesnoTypes;
		}


			/**
			* 获取： 商品审核的值
			*/
			public String getShangpinYesnoValue() {
				return shangpinYesnoValue;
			}
			/**
			* 设置： 商品审核的值
			*/
			public void setShangpinYesnoValue(String shangpinYesnoValue) {
				this.shangpinYesnoValue = shangpinYesnoValue;
			}

		/**
		* 获取： 审核结果
		*/
		public String getShangpinYesnoText() {
			return shangpinYesnoText;
		}
		/**
		* 设置： 审核结果
		*/
		public void setShangpinYesnoText(String shangpinYesnoText) {
			this.shangpinYesnoText = shangpinYesnoText;
		}
		/**
		* 获取： 是否上架
		*/
		public Integer getShangxiaTypes() {
			return shangxiaTypes;
		}
		/**
		* 设置： 是否上架
		*/
		public void setShangxiaTypes(Integer shangxiaTypes) {
			this.shangxiaTypes = shangxiaTypes;
		}


			/**
			* 获取： 是否上架的值
			*/
			public String getShangxiaValue() {
				return shangxiaValue;
			}
			/**
			* 设置： 是否上架的值
			*/
			public void setShangxiaValue(String shangxiaValue) {
				this.shangxiaValue = shangxiaValue;
			}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getShangpinDelete() {
			return shangpinDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setShangpinDelete(Integer shangpinDelete) {
			this.shangpinDelete = shangpinDelete;
		}

		/**
		* 获取： 商品简介
		*/
		public String getShangpinContent() {
			return shangpinContent;
		}
		/**
		* 设置： 商品简介
		*/
		public void setShangpinContent(String shangpinContent) {
			this.shangpinContent = shangpinContent;
		}
	//级联表的get和set 用户

		/**
		* 获取： 用户姓名
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 用户姓名
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 联系方式
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 联系方式
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 用户头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 用户头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 电子邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 电子邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}

		/**
		* 获取： 余额
		*/
		public Double getNewMoney() {
			return newMoney;
		}
		/**
		* 设置： 余额
		*/
		public void setNewMoney(Double newMoney) {
			this.newMoney = newMoney;
		}


	@Override
	public String toString() {
		return "CartView{" +
			", shangpinName=" + shangpinName +
			", shangpinPhoto=" + shangpinPhoto +
			", shangpinKucunNumber=" + shangpinKucunNumber +
			", shangpinOldMoney=" + shangpinOldMoney +
			", shangpinNewMoney=" + shangpinNewMoney +
			", shangpinClicknum=" + shangpinClicknum +
			", shangpinYesnoText=" + shangpinYesnoText +
			", shangpinDelete=" + shangpinDelete +
			", shangpinContent=" + shangpinContent +
			", yonghuName=" + yonghuName +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuEmail=" + yonghuEmail +
			", newMoney=" + newMoney +
			"} " + super.toString();
	}
}
