package com.marcos.money.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money")
public class MoneyApiProperty {
	
	private String originPermitida = "http://localhost:8080";
	private final Seguraca seguraca = new Seguraca();
	
	
	public Seguraca getSeguraca() {
		return seguraca;
	}
	
	
	


	public String getOriginPermitida() {
		return originPermitida;
	}





	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}





	public static class Seguraca{
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
		
	}

}
