package me.calebbfmv.minereach.hub.db;

public enum Column {

	 CURRENCY_TABLE("`PlayerCoins`"),
	 KEY_PLAYER("`Player`"),
	 COIN_BALANCE("`Coins`");
	 
	 private String cName;
	 
	 private Column(String name){
		 this.cName = name;
	 }
	 
	 @Override
	 public String toString(){
		 return cName;
	 }
	 
	 public String formatted(){
		 return cName.replace("`", "");
	 }
	
}
