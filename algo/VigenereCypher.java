package algo;

import javax.swing.JOptionPane;

public class VigenereCypher{
	public static String encrypt(String str){
		String Str = str.toUpperCase();
		String keyword = JOptionPane.showInputDialog("Enter the Key:(ALPHABETS ONLY) ");
		String Key = generateKey(Str, keyword);
		keyword=Key.toUpperCase();
		//String cipher_text = cipherText(Str, Key);
		String cipher_text="";

		for (int i = 0; i < Str.length(); i++){
			/*if condition to skip spaces between alphabest*/
			if(Str.charAt(i)==' '){
				cipher_text+=(char)Str.charAt(i);
			}
			else{
			// converting in range 0-25
			int x = (Str.charAt(i) + Key.charAt(i)) %26;

			// convert into alphabets(ASCII)
			x += 'A';

			cipher_text+=(char)(x);
			}
		}
		return cipher_text;
	}
	
	public static String decrypt(String cipher_text){
		String Str =cipher_text.toUpperCase();
		String keyword = JOptionPane.showInputDialog("Enter the Key:(ALPHABETS ONLY) ");
		String Key = generateKey(Str, keyword);
		keyword = Key.toUpperCase();
		//String cipher_text = cipherText(Str, Key);
		String orig_text="";

		for (int i = 0 ; i < cipher_text.length() && i < Key.length(); i++){
			/*if condition to skip spaces between alphabest*/
			if(Str.charAt(i)==' '){
				orig_text+=(char)Str.charAt(i);
			}
			else{
			// converting in range 0-25
			int x = (cipher_text.charAt(i) - Key.charAt(i) + 26) %26;
			// convert into alphabets(ASCII)
			x += 'A';
			orig_text+=(char)(x);
			}
		}
		return orig_text;
	}
	
	public static String generateKey(String str, String key){
		int x = str.length();
		for (int i = 0; i<=x; i++){
			if (x == i)
				i = 0;
			if (key.length() == str.length())
				break;
			key+=(key.charAt(i));
		}
		return key;
	}
}