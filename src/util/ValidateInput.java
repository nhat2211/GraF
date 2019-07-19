package util;

public class ValidateInput {
	
	public static boolean userWantToCreate(String typeEdge,String edgeWeight) {
		if(isNullOrEmpty(typeEdge) && isNullOrEmpty(edgeWeight)) {
			return false;
		}else {
			return true;
		}
		
		
	}
	
	public static boolean isNullOrEmpty(String input) {
		if(input != null && !input.isEmpty()) {
			return false;
		}else {
			return true;
		}
		
		
	}

}
