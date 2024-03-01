package algo;

public class CaesarCypher{
	public static StringBuffer encrypt(String text, int k){
		int key = 5;
		StringBuffer result= new StringBuffer();
        for (int i=0;i<text.length();i++){
			char ch = ' ';
			boolean b = true;
			/*if condition to skip anything (Either Numbers or Symbols) other than alphabest*/
			if(b!=(text.charAt(i)>='a' && text.charAt(i)<='z') && b!=(text.charAt(i)>='A' && text.charAt(i)<='Z')){
				ch=text.charAt(i);
				result.append(ch);
			}
			if(Character.isUpperCase(text.charAt(i))){
				ch = (char)(((int)text.charAt(i)+key-65)%26+65);
				result.append(ch);
			}
			if(Character.isLowerCase(text.charAt(i))){
				ch = (char)(((int)text.charAt(i)+key-97)%26+97);
				result.append(ch);
			}
		}
		return result;
	}
	
	public static StringBuffer decrypt(String text,int k){
		int key = 5;
		StringBuffer result= new StringBuffer();
        for (int i=0;i<text.length();i++){
			char ch = ' ';
			boolean b = true;
			/*if condition to skip anything (Either Numbers or Symbols) other than alphabest*/
			if(b!=(text.charAt(i)>='a' && text.charAt(i)<='z') && b!=(text.charAt(i)>='A' && text.charAt(i)<='Z')){
				ch=text.charAt(i);
				result.append(ch);
			}
			if(Character.isUpperCase(text.charAt(i))){
				ch = (char)(((int)text.charAt(i)-key-65)%26+65);
				result.append(ch);
			}
			if(Character.isLowerCase(text.charAt(i))){
				ch = (char)(((int)text.charAt(i)-key-97)%26+97);
				result.append(ch);
			}
		}
		return result;
	}
}