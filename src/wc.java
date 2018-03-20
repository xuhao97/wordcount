import java.io.*;


public class wc {
    public static void main(String[] args) {
    	int i=0;
    	boolean out=false;
        String pathname="";
        String outputpath="";
    	for(i=0;i<args.length;i++)
    	{
		if(args[i].endsWith(".c")) 	pathname=args[i];
		if(args[i].equals("-o")) out=true;  
    	}
        if(out)
        {
        	i=args.length;
        	outputpath=args[i-1];
        }

      	File f = new File(pathname);
    	for(i=0;i<args.length;i++)//�жϴ���Ĳ�������ִ����Ӧ�Ĳ���
    	{
    		if(args[i].equals("-c")) c(f,out,pathname,outputpath);
    		if(args[i].equals("-w")) w(f,out,pathname,outputpath);
    		if(args[i].equals("-l")) l(f,out,pathname,outputpath);  		
    		
    	}
    }
    
    public static int c(File f,boolean out,String path,String opath){
       	int num=0;
    	BufferedReader reader = null;
    	try {
       	reader = new BufferedReader(new FileReader(f));
    	String s = null;
      	while ((s = reader.readLine()) != null){ 
      		 num += s.length();
      		}
      	if(out)
      	{
      		FileWriter fw = null;
            File ft = new File(opath);
            fw = new FileWriter(ft, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(path+",�ַ���:"+num);
            pw.flush();
            try {
            fw.flush();
            pw.close();
            fw.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
      	}
      	}
    	catch (FileNotFoundException e2) {
    	e2.printStackTrace();
    	} 
    	catch (IOException e1) {
    	e1.printStackTrace();
    	}
    	finally{
      	try{
      		reader.close();
      	}
        catch (IOException e1) {
        e1.printStackTrace();
        }
    	}
    	return num;
        }
    


     
      public static int w(File f,boolean out,String path,String opath){
         	int word=0;
        	BufferedReader reader = null;
        	try {
           	reader = new BufferedReader(new FileReader(f));
        	String s = null;
          	while ((s = reader.readLine()) != null){
          		if(s.charAt(0)==32) word=word-1;//�����һ���ǿո��򵥴�����Ҫ��һ����Ϊspilt��ѿո�ǰ��Ϊһ������
          		 word += s.split(",|\\s+|\t").length;
          	}
          	if(out)
          	{
          		FileWriter fw = null;
                File ft = new File(opath);
                fw = new FileWriter(ft, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(path+",������:"+word);
                pw.flush();
                try {
                fw.flush();
                pw.close();
                fw.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
          	}
        	}
        	catch (FileNotFoundException e2) {
        	e2.printStackTrace();
        	} 
        	catch (IOException e1) {
        	e1.printStackTrace();
        	}
        	finally{
          	try{
          		reader.close();
          	}
            catch (IOException e1) {
            e1.printStackTrace();
            }
        	}
        	return word;
            }
      
      public static int l(File f,boolean out,String path,String opath){
       	int line=0;
      	BufferedReader reader = null;
      	try {
         	reader = new BufferedReader(new FileReader(f));
      	String s = null;
        	while ((s = reader.readLine()) != null)
        line +=1;
          	if(out)
          	{
          		FileWriter fw = null;
                File ft = new File(opath);
                fw = new FileWriter(ft, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(path+",����:"+line);
                pw.flush();
                try {
                fw.flush();
                pw.close();
                fw.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
          	}
         }
      	catch (FileNotFoundException e2) {
      	e2.printStackTrace();
      	} 
      	catch (IOException e1) {
      	e1.printStackTrace();
      	}
      	finally{
        	try{
        		reader.close();
        	   }
          catch (IOException e1) {
          e1.printStackTrace();
          }
      	}
      	return line;
      	} 
}



     

    
  	  
      
    	  

      
     
	

