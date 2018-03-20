import java.io.*;
import java.util.*;

public class wc {
    static List<String> names = new ArrayList<String>();
    public static void main(String[] args) throws IOException {
    	int i,j=0;
    	boolean out=false,stop=false;
        String pathname="";
        String outputpath="";
        String stoppath="";
        String[] newpath={};
        boolean already=false;
        for(i=0;i<args.length;i++)//���ж��Ƿ�Ҫ������ļ����Լ�ͣ�ôʱ�
        {
		if(args[i].endsWith(".c")) 	pathname=args[i];
		if(args[i].equals("-o")) 
			{
			out=true; 
			outputpath=args[i+1]; //��ȡ����ļ�λ��
			}
		if(args[i].equals("-e")) 
			{
			stop=true;
			stoppath=args[i+1]; //��ȡͣ�ôʱ�λ��
			}
		
    	}  
        for(i=0;i<args.length;i++)//�������Ƚ����࣬Ϊ�˼�������Ҷ���-s�����Ľ����˵���������ΪҪ�������ļ�
        {
 		if(args[i].equals("-s")) 
		{
 			already=true;
        String path=args[i+1];  //��ȡ�ļ���λ��
        readfile(path);
        for (j = 0; j < names.size(); j++) {
        	File f = new File(names.get(j)); 
        	pathname=names.get(j);
        	for(i=0;i<args.length;i++)//�жϴ���Ĳ�������ִ����Ӧ�Ĳ���
        	{
    		if(args[i].equals("-c")) c(f,out,pathname,outputpath);
    		if(args[i].equals("-w")) w(f,out,pathname,outputpath,stop,stoppath);
    		if(args[i].equals("-l")) l(f,out,pathname,outputpath);
    		if(args[i].equals("-a")) a(f,out,pathname,outputpath);
        	}
        }
		}
        }
      	File f = new File(pathname);
      	if(!already)//-s�Լ����й��ˣ���������ִ�еĲ����ǲ���-s��
      	{
    	for(i=0;i<args.length;i++)//�жϴ���Ĳ�������ִ����Ӧ�Ĳ���
    	{
    		if(args[i].equals("-c")) c(f,out,pathname,outputpath);
    		if(args[i].equals("-w")) w(f,out,pathname,outputpath,stop,stoppath);
    		if(args[i].equals("-l")) l(f,out,pathname,outputpath);
    		if(args[i].equals("-a")) a(f,out,pathname,outputpath);    		
    	}
      	}
    }
    
	 public static void readfile(String filepath) throws FileNotFoundException, IOException {//�����ļ��Ĺ���
         int i;
		 try {        	  
                 File file = new File(filepath);
                 if (!file.isDirectory()) {//��������ļ��У�ֱ�ӻ�ȡ�ļ����ֲ��ж�
                     if(file.getAbsolutePath().endsWith(".c")) {                  	                
                    	 names.add(file.getAbsolutePath());
                     }
                 } else if (file.isDirectory()) {//������ļ��У�������ļ��У������ļ����е��ļ��н��еݹ����
                         String[] filelist = file.list();
                         for (i = 0; i < filelist.length; i++) {
                                 File readfile = new File(filepath + "\\" + filelist[i]);
                                 if (!readfile.isDirectory()) {
                                     if(readfile.getAbsolutePath().endsWith(".c")) {                                   	 
                                    	 names.add(readfile.getAbsolutePath());
                                     }
                                 } else if (readfile.isDirectory()) {
                                         readfile(filepath + "\\" + filelist[i]);//�ݹ����
                                 }
                         }

                 }

         } catch (FileNotFoundException e) {
                 System.out.println("readfile()   Exception:" + e.getMessage());
         }
       	
}
	     	
    
    public static int c(File f,boolean out,String path,String opath){
       	int num=0;
    	BufferedReader reader = null;
    	try {
       	reader = new BufferedReader(new FileReader(f));
    	String s = null;
      	while ((s = reader.readLine()) != null){ //��ȡÿһ��
      		 num += s.length();//ֱ�Ӽ�����һ�е��ַ���
      		}
      	if(out)//�ж��Ƿ������ͨ����������
      	{
      		FileWriter fw = null;//׷�����
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
    


     
      public static int w(File f,boolean out,String path,String opath,boolean stop,String spath){
         	int word=0;
         	int k=0,j=0;
        	BufferedReader reader = null;
        	BufferedReader stopreader = null;
        	String sl;
        	String[] wordlist={};
        	String[] stoplist={};
        	if(stop)
        	{
            try {
        	stopreader = new BufferedReader(new FileReader(spath));
        	while ((sl = stopreader.readLine()) != null){
        		stoplist=sl.split("\\s+");}//��������ʽ�����жϣ�\\s����һ�пհ�,+�����������ԱȽ����׵ĵó����ʸ�����
        	}
        	catch (FileNotFoundException e2) {
            	e2.printStackTrace();
            	} 
            	catch (IOException e1) {
            	e1.printStackTrace();
            	}
        	}
        	try {
           	reader = new BufferedReader(new FileReader(f));
        	String s = null;
          	while ((s = reader.readLine()) != null){
          		if(s.charAt(0)==32||s.charAt(0)==44||s.charAt(0)==9) word=word-1;//�����һ���ǿո��򵥴�����Ҫ��һ����Ϊspilt��ѿո�ǰ��Ϊһ������
          		 word += s.split(",+|\\s+").length;
          		 if(stop)
          		 {
          			wordlist=s.split(",+|\\s+");//ȡ������
          			for(k=0;k<wordlist.length;k++)
          			{
          				for(j=0;j<stoplist.length;j++)
          				{wordlist[k]=wordlist[k].trim();
          				if(wordlist[k].equals(stoplist[j]))//���������ͬ�����ȥһ����Ϊ֮ǰͳ����һ��)
          				{
          					word = word-1;
          				}
          				}
          			}
          		 }
          	}
          	if(out)//���
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
        	while ((s = reader.readLine()) != null)//��ȡһ�����һ����
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
      
	public static void a(File f,boolean out,String path,String opath){
         	int blankline=0;
         	int noteline=0;
         	int codeline=0;
         	int line=0;
         	int i=0;
        	String sa = null;
         	boolean notnote=true;//�����ж��Ƿ���/*������У���Ϊ�������о���ע���У������ô˱��ǰ���Ƿ������/*��û�г���*/
        	BufferedReader reader = null;
        	try {
           	reader = new BufferedReader(new FileReader(f));
          	while ((sa = reader.readLine()) != null)
          	{
          		line +=1;//ͳ��������
              if(sa.length()<=1) blankline +=1;//���һ���ַ�������һ��ֱ����Ϊ�հ��У���ʹ�п��ܴ�����/*....*/��
              else{
            	     if((!sa.contains("*/"))&&!notnote) noteline+=1; //�����ǲ�ͬ������ж�
                	 if(sa.contains("/*")&&notnote) 
                	  {
                		  notnote=false;
                		  noteline+=1;
                	  }
                	  if(sa.contains("//")&&notnote) noteline+=1;
                      if(sa.contains("*/")&&!notnote) 
                		  {
                		  noteline+=1;
                	      notnote=true; 
                		  }           
              }
          	}
              codeline=line-noteline-blankline;//������ֱ������������ȥ�հ��к�ע���о�����
            	if(out)
            	{
            		FileWriter fw = null;
                  File ft = new File(opath);
                  fw = new FileWriter(ft, true);
                  PrintWriter pw = new PrintWriter(fw);
                  pw.println(path+",������/����/ע����:"+codeline+"/"+blankline+"/"+noteline);
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
        	} 
				 
	
}



     

    
  	  
      
    	  

      
     
	

