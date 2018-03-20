import java.io.*;
import java.util.*;

public class wc {
    public static void main(String[] args) throws IOException {
    	int i=0;
    	boolean out=false,stop=false;
        List<String>multipath=new ArrayList<String>();
        String pathname="";
        String outputpath="";
        String stoppath="";
        boolean already=false;
        for(i=0;i<args.length;i++)
        {
		if(args[i].endsWith(".c")) 	pathname=args[i];
		if(args[i].equals("-o")) 
			{
			out=true; 
			outputpath=args[i+1];
			}
		if(args[i].equals("-e")) 
			{
			stop=true;
			stoppath=args[i+1];
			}
		
    	}  
        for(i=0;i<args.length;i++)
        {
 		if(args[i].equals("-s")) 
		{
 			already=true;
        String path=args[i+1];        
        multipath =readfile(path);
		for (i = 0; i < multipath.size(); i++) {
			System.out.println(multipath.get(i));
		}
        for (i = 0; i < multipath.size(); i++) {
        	File f = new File(multipath.get(i)); 
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
      	if(!already)
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
    
	 public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
         int i;
         List<String> names = new ArrayList<String>();
		 try {        	  
                 File file = new File(filepath);
                 if (!file.isDirectory()) {
                     if(file.getAbsolutePath().endsWith(".c")) {                  	                
                    	 names.add(file.getAbsolutePath());
                     }
                 } else if (file.isDirectory()) {
                         String[] filelist = file.list();
                         for (i = 0; i < filelist.length; i++) {
                                 File readfile = new File(filepath + "\\" + filelist[i]);
                                 if (!readfile.isDirectory()) {
                                     if(readfile.getAbsolutePath().endsWith(".c")) {                                   	 
                                    	 names.add(readfile.getAbsolutePath());
                                     }
                                 } else if (readfile.isDirectory()) {
                                         readfile(filepath + "\\" + filelist[i]);
                                 }
                         }

                 }

         } catch (FileNotFoundException e) {
                 System.out.println("readfile()   Exception:" + e.getMessage());
         }

		return names;
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
        		stoplist=sl.split("\\s+");}
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
          			wordlist=s.split(",+|\\s+");
          			for(k=0;k<wordlist.length;k++)
          			{
          				for(j=0;j<stoplist.length;j++)
          				{wordlist[k]=wordlist[k].trim();
          				if(wordlist[k].equals(stoplist[j]))
          				{
          					word = word-1;
          				}
          				}
          			}
          		 }
          	}
          	if(out)
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
        	while ((s = reader.readLine()) != null)
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
         	boolean notnote=true;
        	BufferedReader reader = null;
        	try {
           	reader = new BufferedReader(new FileReader(f));
          	while ((sa = reader.readLine()) != null)
          	{
          		line +=1;
              if(sa.length()<=1) blankline +=1;
              else{
            	     if((!sa.contains("*/"))&&!notnote) noteline+=1;       
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
              codeline=line-noteline-blankline;
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



     

    
  	  
      
    	  

      
     
	

