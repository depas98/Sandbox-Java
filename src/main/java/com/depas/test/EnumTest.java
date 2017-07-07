package com.depas.test;

public class EnumTest {

	public enum BeerStyle {
		IPA("IPA","Blind Pig"),
		DOUBLE_IPA("Doulbe IPA","Pliny the Elder"),
		AMERICAN_PALE_ALE("American Pale Ale","Sierra Nevada"),
		BROWN("Brown","Elle's Brown Ale");

		private String displayName;
		private String example;

		BeerStyle (String displayName, String example){
			this.displayName=displayName;
			this.example=example;
		}

		String getDisplaName(){return displayName;}
		String getExample(){return example;}
		
		static BeerStyle getEnumFromDisplayName(String displayName){
			 for (BeerStyle beerStyle : BeerStyle.values()) {
			    if (beerStyle.getDisplaName().equals(displayName)){
			    	return beerStyle;
			    }
			 }
			 
			 return null;
		}

	};
	
	public static void main(String[] args) {
				
		 for (BeerStyle beerStyle : BeerStyle.values()) {
		     System.out.println("Style: " + beerStyle + " Display Name: " + beerStyle.getDisplaName() + " Example: " + beerStyle.getExample());
		 }		

		 System.out.println("");
		 System.out.println(BeerStyle.AMERICAN_PALE_ALE.name());
		 
		 System.out.println("");
		 System.out.println(BeerStyle.valueOf("DOUBLE_IPA").getDisplaName());
		 
		 
		 System.out.println(BeerStyle.getEnumFromDisplayName("American Pale Ale").getExample());
	}
}