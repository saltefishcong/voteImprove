package com.vote.eity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("optionkey")
public class optionkey {     //���ѡ�����ֺ�ѡ��ֵ����,��Ϊ���������ڷ�װ��json�����ʱ���ó���,��ȥ���ַ������Ҫת�����鷳
	   public optionkey(String optionkey, Integer optionvalue) { 
			super();
			this.optionkey = optionkey;
			this.optionvalue = optionvalue;
		}
		private  String optionkey;
		   private  Integer optionvalue;
		public String getOptionkey() {
			return optionkey;
		}
		public void setOptionkey(String optionkey) {
			this.optionkey = optionkey;
		}
		public Integer getOptionvalue() {
			return optionvalue;
		}
		public void setOptionvalue(Integer optionvalue) {
			this.optionvalue = optionvalue;
		}
		public optionkey(){
			
		}
		public String toString(){
			return "( Key: " + this.optionkey + " Value: " + this.optionvalue + " )";
		}
}
