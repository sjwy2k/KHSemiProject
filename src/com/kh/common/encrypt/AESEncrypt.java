package com.kh.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESEncrypt {
	//AES방식으로 양방향 암호화하는 객체
	//생성과 동시에 암호화 키를 생성, 또는 기존 키를 가져오기
	//암호화 처리  메서드 작성
	//복호화(해독) 처리 메서드 작성
	
	
	//키 값을 저장하는 객체 : SecretKey객체로 생성된 key값을 저장
	//*암호화 관련 패키지 javax.crypto패키지에 만들어져 있음
	
	private static SecretKey key;
	private String path;
	
	public AESEncrypt(String path) {
		//생성자, 객체가 생성될 때 key값을 초기화하자
		//서버가 올라가면 context영역이 생기므로 listener를 만들어서 서버 올라갈 때 한번 실행시킬 수 있도록 한다

		this.path=path;
		File f=new File(path+"/secret.bs");
		System.out.println("파일있니?"+f.exists());
		if(f.exists()) {//파일이 있으면
			//있는 파일을 불러온다
			try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));){
				//객체를 읽어들이고 secretkey로 형변환
				key=(SecretKey)ois.readObject();
				
			}catch(IOException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else { //없으면
			//키를 생성한 적이 없으므로 새로 만듬
			try {
				generateKey();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
	
	//key값을 생성하는 메서드
	private void generateKey() throws NoSuchAlgorithmException{
		//암호화 키를 위한 솔트값(암호화 키가 중복되면 않되므로 랜덤값으로 넣는다)
		SecureRandom sr=new SecureRandom(); 
		//key를 생성해서 저장하는 객체
		KeyGenerator keyGen;
		
		//AES방식의 key값을 생성
		keyGen=KeyGenerator.getInstance("AES");
		//생성된 알고리즘으로 key값 초기화 시키기
		keyGen.init(128,sr);//키값을 초기화 설정
		
		//key값 생성
		key=keyGen.generateKey();
		
		//생성된 key값은 파일로 저장하여 관리함(계속 생성되면 안 되므로 영속적으로 저장)
		File f=new File(path+"/secret.bs");

		//WEB-INF로 저장
		//TryWithResource
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f));) {
			oos.writeObject(key);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//암호화 메서드 만들기
	//필요한 곳에서 사용할 수 있게 static으로 작성
	public static String encrypt(String str) { //암호화 할 내용 입력
		
		byte[] enStr=null;
		try {
			//Cipher객체를 이용하여 암호화 진행
			Cipher cipher=Cipher.getInstance("AES");
			//cipher설정, 암호화용 key값 지정
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//암호화 처리는 바이트단위
			enStr=cipher.doFinal(str.getBytes());
			
		}catch(Exception e) {
			e.printStackTrace();
			//NoSuchAlgorithmException : 해당하는 알고리즘이 없을 때
			//NoSuchPaddingException : 암호화 할 때
			//IllegalBlockSizeException : 
			//BadPaddingException :
			//InvalidKeyException : 키가 잘못되었을 때
		}
		//암호화 마친 바이트 스트링을 String으로 변환(Base64)
		//enStr이 null이 아니면 변환하고 enStr이 null이면 str그대로 반환
		return enStr!=null?Base64.getEncoder().encodeToString(enStr):str;
	}
	
	//복호화 메서드 만들기
	public static String decrypt(String str) throws UnsupportedEncodingException {
		byte[] deStr=null;
		try {
			Cipher cipher=Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			//디코딩도 마찬가지로 바이트단위로 디코드
			//해제하는 작업이므로 암호화의 역순으로 진행한다(디코드를 먼저 하고 바이트 배열로 바꾼다)
			byte[] decodeStr=Base64.getDecoder().decode(str.getBytes());
			deStr=cipher.doFinal(decodeStr);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new String(deStr,"UTF-8");
	}
}
