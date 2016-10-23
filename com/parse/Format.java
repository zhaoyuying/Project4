package com.parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
	
	 public static void main(String[]  args){
	        try {
	            test1();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	     
	    public static void test1()throws Exception{
	        FileInputStream in=new FileInputStream("demo.jsp");
	        InputStreamReader ins=new InputStreamReader(in,"UTF-8");
	        BufferedReader reader=new BufferedReader(ins);
	         
	        FileOutputStream out=new FileOutputStream("result.jsp");
	        OutputStreamWriter outs=new OutputStreamWriter(out,"UTF-8");
	        BufferedWriter writer=new BufferedWriter(outs);
	        StringBuffer str=new StringBuffer("");
	        String temp;
	        while ((temp = reader.readLine()) != null) { //���ж�ȡ�����
	            if(!temp.trim().equals("")){
	                str.append(temp); //����ÿ�к���
	            }
	        }
	        String input=str.toString();
	        input=input.replaceAll("\t", "");
	        input=input.replaceAll("\n", "");
	         
	        while(true) {
	            if(input.length()==0){
	                break;
	            }
	            String tag=findMatch(input, "<[^>]+>");//��<...>��ǩ
	            if(tag!=null){
	                if(tag.matches("<[^<]+>")){
	                    tag=findMatch(input, "<[^>]+>[^<]+");//��<...>��ǩ����һ����ǩǰ������
	                    if(tag==null){
	                        tag=findMatch(input, "<[^>]+>");
	                    }
	                    writer.write(tag);
	                    writer.write("\n");
	                    input=input.substring(tag.length());
	                    tag="";
	                }else if(tag.matches("<.+[<].+>")){//�����<...<...>
	                    String regxp="<.+?";
	                    String regx="<[^<]+>";
	                    String regxe=">";
	                    int index=0;
	                    while(true){
	                        index++;
	                        String regxm="";
	                        for(int i=0;i<index;i++){
	                            regxm+=regx+"[^<]+";
	                        }
	                        tag=findMatch(input, regxp+regxm+regxe);//ƥ���<...<...>...>
	                        if(tag!=null){
	                            break;
	                        }
	                    }
	                    writer.write(tag);
	                    writer.write("\n");
	                    input=input.substring(tag.length());
	                }           
	            }else{
	                System.err.println(input);
	                break;
	            }
	            writer.flush();
	            writer.close();
	            reader.close();
	        }
	    }
	     
	    private static String findMatch(String input,String regx){
	        Pattern p = Pattern.compile(regx);
	        Matcher m = p.matcher(input);
	        boolean find=m.find();
	        if(find&&input.startsWith(m.group(0))){
	            return m.group(0);
	        }else{
	            return null;
	        }
	    }

}
