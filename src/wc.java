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
        for(i=0;i<args.length;i++)//先判断是否要输出到文件，以及停用词表
        {
		if(args[i].endsWith(".c")) 	pathname=args[i];
		if(args[i].equals("-o")) 
			{
			out=true; 
			outputpath=args[i+1]; //获取输出文件位置
			}
		if(args[i].equals("-e")) 
			{
			stop=true;
			stoppath=args[i+1]; //获取停用词表位置
			}
		
    	}  
        for(i=0;i<args.length;i++)//这里代码比较冗余，为了简单起见，我对有-s参数的进行了单独处理，因为要处理多个文件
        {
 		if(args[i].equals("-s")) 
		{
 			already=true;
        String path=args[i+1];  //获取文件夹位置
        readfile(path);
        for (j = 0; j < names.size(); j++) {
        	File f = new File(names.get(j)); 
        	pathname=names.get(j);
        	for(i=0;i<args.length;i++)//判断传入的参数，并执行相应的步骤
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
      	if(!already)//-s以及运行过了，所以这里执行的参数是不含-s的
      	{
    	for(i=0;i<args.length;i++)//判断传入的参数，并执行相应的步骤
    	{
    		if(args[i].equals("-c")) c(f,out,pathname,outputpath);
    		if(args[i].equals("-w")) w(f,out,pathname,outputpath,stop,stoppath);
    		if(args[i].equals("-l")) l(f,out,pathname,outputpath);
    		if(args[i].equals("-a")) a(f,out,pathname,outputpath);    		
    	}
      	}
    }
    
	 public static void readfile(String filepath) throws FileNotFoundException, IOException {//遍历文件的过程
         int i;
		 try {        	  
                 File file = new File(filepath);
                 if (!file.isDirectory()) {//如果不是文件夹，直接获取文件名字并判断
                     if(file.getAbsolutePath().endsWith(".c")) {                  	                
                    	 names.add(file.getAbsolutePath());
                     }
                 } else if (file.isDirectory()) {//如果是文件夹，则进入文件夹，并在文件夹中的文件夹进行递归调用
                         String[] filelist = file.list();
                         for (i = 0; i < filelist.length; i++) {
                                 File readfile = new File(filepath + "\\" + filelist[i]);
                                 if (!readfile.isDirectory()) {
                                     if(readfile.getAbsolutePath().endsWith(".c")) {                                   	 
                                    	 names.add(readfile.getAbsolutePath());
                                     }
                                 } else if (readfile.isDirectory()) {
                                         readfile(filepath + "\\" + filelist[i]);//递归调用
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
      	while ((s = reader.readLine()) != null){ //读取每一行
      		 num += s.length();//直接计算这一行的字符数
      		}
      	if(out)//判断是否输出，通过参数传入
      	{
      		FileWriter fw = null;//追加输出
            File ft = new File(opath);
            fw = new FileWriter(ft, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(path+",字符数:"+num);
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
        		stoplist=sl.split("\\s+");}//用正则表达式进行判断，\\s代表一切空白,+代表多个。可以比较容易的得出单词个数。
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
          		if(s.charAt(0)==32||s.charAt(0)==44||s.charAt(0)==9) word=word-1;//如果第一个是空格，则单词数需要减一，因为spilt会把空格前作为一个单词
          		 word += s.split(",+|\\s+").length;
          		 if(stop)
          		 {
          			wordlist=s.split(",+|\\s+");//取出单词
          			for(k=0;k<wordlist.length;k++)
          			{
          				for(j=0;j<stoplist.length;j++)
          				{wordlist[k]=wordlist[k].trim();
          				if(wordlist[k].equals(stoplist[j]))//如果单词相同，则减去一（因为之前统计了一次)
          				{
          					word = word-1;
          				}
          				}
          			}
          		 }
          	}
          	if(out)//输出
          	{
          		FileWriter fw = null;
                File ft = new File(opath);
                fw = new FileWriter(ft, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(path+",单词数:"+word);
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
        	while ((s = reader.readLine()) != null)//读取一行则加一即可
        line +=1;
          	if(out)
          	{
          		FileWriter fw = null;
                File ft = new File(opath);
                fw = new FileWriter(ft, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(path+",行数:"+line);
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
         	boolean notnote=true;//这里判断是否是/*后面的行，因为这后面的行均是注释行，所以用此标记前面是否出现了/*且没有出现*/
        	BufferedReader reader = null;
        	try {
           	reader = new BufferedReader(new FileReader(f));
          	while ((sa = reader.readLine()) != null)
          	{
          		line +=1;//统计总行数
              if(sa.length()<=1) blankline +=1;//如果一行字符数少于一，直接视为空白行，即使有可能此行在/*....*/中
              else{
            	     if((!sa.contains("*/"))&&!notnote) noteline+=1; //这里是不同情况的判断
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
              codeline=line-noteline-blankline;//代码行直接用总行数减去空白行和注释行就行了
            	if(out)
            	{
            		FileWriter fw = null;
                  File ft = new File(opath);
                  fw = new FileWriter(ft, true);
                  PrintWriter pw = new PrintWriter(fw);
                  pw.println(path+",代码行/空行/注释行:"+codeline+"/"+blankline+"/"+noteline);
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



     

    
  	  
      
    	  

      
     
	

