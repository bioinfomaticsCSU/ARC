//package shellproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FilterLowAndHighFreReadLong {
	
	public static int getNucmerLine(String ReadSetPath) throws IOException //Get the number of line of ReadSet.
    {
         int line=0; 
         String encoding = "utf-8";
         String readtemp="";
         File file = new File(ReadSetPath);
         if (file.isFile() && file.exists()) 
         { 
             InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
             BufferedReader bufferedReader = new BufferedReader(read);
             while ((readtemp=bufferedReader.readLine()) != null)
             { 
                   if(readtemp.charAt(0)!='>')
                   {
                  	   line++;
                   }     
             } 
             bufferedReader.close();
         }
         return line;
    }
    
	public static int readTxtFile(String ReadSetPath, String[] ReadSetArray) 
	{
		int count = 0;
		try {
			  String readtemp ="";
			  String encoding = "utf-8";
			  File file = new File(ReadSetPath);
			  if (file.isFile() && file.exists()) {
				  InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); 
				  BufferedReader bufferedReader = new BufferedReader(read);
				  while ((readtemp = bufferedReader.readLine()) != null) {
					  if(readtemp.charAt(0)!='>'){
						  ReadSetArray[count++]=readtemp;
					  }
				  }
				  bufferedReader.close();
			  } 
			  else {
				  System.out.println("File is not exist!");
			  }
		} catch (Exception e) {
			System.out.println("Error liaoxingyu");
			e.printStackTrace();
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
        String ReadSetLeft=args[0];
        String ReadSetRight=args[1];
        String NormalReadSetPath=args[2];
		String ReadLength=args[3];
		String FillStr="A";
		for(int t=0;t<Integer.parseInt(ReadLength)-1;t++)
		{
			FillStr+="A";
		}
        //Loading.
	    int LineOfReadSetLeft=getNucmerLine(ReadSetLeft);
        int LineOfReadSetRight=getNucmerLine(ReadSetRight);
        String ReadSetArrayLeft[] = new String[LineOfReadSetLeft];
        String ReadSetArrayRight[] = new String[LineOfReadSetRight];
        int CountLeft=readTxtFile(ReadSetLeft, ReadSetArrayLeft);
        int CountRight=readTxtFile(ReadSetRight, ReadSetArrayRight);
        System.out.println("The size of left readset is:"+CountLeft);
        System.out.println("The size of right readset is:"+CountRight);
        //process
		int LowNum=0;
		int HighNum=0;
		int AbNum=0;
        for(int t=0;t<CountLeft;t++)
        {
        	if(ReadSetArrayLeft[t].charAt(0)=='#')
        	{
        	    if(ReadSetArrayRight[t].charAt(0)=='#')   
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close(); 
					//
			        FileWriter writer2= new FileWriter(NormalReadSetPath+"ReadSet_short2.LowFre.fasta",true);
                    writer2.write(">"+(LowNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(LowNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer2.close();
					//
					ReadSetArrayLeft[t]="@"+ReadSetArrayLeft[t];
                    ReadSetArrayRight[t]="@"+ReadSetArrayRight[t];
        	    }
        	    else if(ReadSetArrayRight[t].charAt(0)=='$')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close();
					//
					FileWriter writer3= new FileWriter(NormalReadSetPath+"ReadSet_short2.LowFre.fasta",true);
                    writer3.write(">"+(LowNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(LowNum++)+"\t"+"right"+"\n"+FillStr+"\n");
                    writer3.close();
					//
					FileWriter writer8= new FileWriter(NormalReadSetPath+"ReadSet_short2.HighFre.fasta",true);
                    writer8.write(">"+(HighNum)+"\t"+"left"+"\n"+FillStr+"\n"+">"+(HighNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer8.close();
					//
					ReadSetArrayLeft[t]="@"+ReadSetArrayLeft[t];
                    ReadSetArrayRight[t]="@"+ReadSetArrayRight[t];
        	    }
        	    else if(ReadSetArrayRight[t].charAt(0)!='#' && ReadSetArrayRight[t].charAt(0)!='$')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t]+"\n");
                    writer.close(); 
                    //
					FileWriter writer4= new FileWriter(NormalReadSetPath+"ReadSet_short2.LowFre.fasta",true);
                    writer4.write(">"+(LowNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(LowNum++)+"\t"+"right"+"\n"+FillStr+"\n");
                    writer4.close(); 
					//
				    ReadSetArrayLeft[t]=FillStr;
        	    }
           }
           else if(ReadSetArrayLeft[t].charAt(0)=='$')
           {
          	    if(ReadSetArrayRight[t].charAt(0)=='$')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close(); 
					//
					FileWriter writer5= new FileWriter(NormalReadSetPath+"ReadSet_short2.HighFre.fasta",true);
                    writer5.write(">"+(HighNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(HighNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer5.close(); 
					//
					ReadSetArrayLeft[t]="@"+ReadSetArrayLeft[t];
                    ReadSetArrayRight[t]="@"+ReadSetArrayRight[t];
        	    }
        	    else if(ReadSetArrayRight[t].charAt(0)=='#')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close();
					//
					FileWriter writer6= new FileWriter(NormalReadSetPath+"ReadSet_short2.LowFre.fasta",true);
                    writer6.write(">"+(LowNum)+"\t"+"left"+"\n"+FillStr+"\n"+">"+(LowNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer6.close();
					//
					FileWriter writer9= new FileWriter(NormalReadSetPath+"ReadSet_short2.HighFre.fasta",true);
                    writer9.write(">"+(HighNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(HighNum++)+"\t"+"right"+"\n"+FillStr+"\n");
                    writer9.close();
					//
					ReadSetArrayLeft[t]="@"+ReadSetArrayLeft[t];
                    ReadSetArrayRight[t]="@"+ReadSetArrayRight[t];
        	    }
        	    else if(ReadSetArrayRight[t].charAt(0)!='#' && ReadSetArrayRight[t].charAt(0)!='$')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t]+"\n");
                    writer.close(); 
					//
					FileWriter writer4= new FileWriter(NormalReadSetPath+"ReadSet_short2.HighFre.fasta",true);
                    writer4.write(">"+(HighNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t].substring(1,ReadSetArrayLeft[t].length())+"\n"+">"+(HighNum++)+"\t"+"right"+"\n"+FillStr+"\n");
                    writer4.close(); 
					//
                    ReadSetArrayLeft[t]=FillStr;
        	    }
           }
		   else if(ReadSetArrayLeft[t].charAt(0)!='#' && ReadSetArrayLeft[t].charAt(0)!='$')
		   {
			    if(ReadSetArrayRight[t].charAt(0)=='#')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t]+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close(); 
					//
					FileWriter writer6= new FileWriter(NormalReadSetPath+"ReadSet_short2.LowFre.fasta",true);
                    writer6.write(">"+(LowNum)+"\t"+"left"+"\n"+FillStr+"\n"+">"+(LowNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer6.close();
					//
                    ReadSetArrayRight[t]=FillStr;
        	    }
        	    else if(ReadSetArrayRight[t].charAt(0)=='$')
        	    {
			        FileWriter writer= new FileWriter(NormalReadSetPath+"AbnormalReadSet_short2.fasta",true);
                    writer.write(">"+(AbNum)+"\t"+"left"+"\n"+ReadSetArrayLeft[t]+"\n"+">"+(AbNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer.close();
					//
					FileWriter writer3= new FileWriter(NormalReadSetPath+"ReadSet_short2.HighFre.fasta",true);
                    writer3.write(">"+(HighNum)+"\t"+"left"+"\n"+FillStr+"\n"+">"+(HighNum++)+"\t"+"right"+"\n"+ReadSetArrayRight[t].substring(1,ReadSetArrayRight[t].length())+"\n");
                    writer3.close();
					//
					ReadSetArrayRight[t]=FillStr;
        	    }
		   }
	   }
	   //Final
       for(int r=0;r<CountLeft;r++)
       {
    	   if(ReadSetArrayLeft[r].charAt(0)=='@' && ReadSetArrayRight[r].charAt(0)=='@')
    	   {
    		   continue;
    	   }
    	   else
    	   {
			   FileWriter writer= new FileWriter(NormalReadSetPath+"ReadSet_short2.Filter.fasta",true);
               writer.write(">"+r+"\n"+ReadSetArrayLeft[r]+"\n"+">"+r+"\n"+ReadSetArrayRight[r]+"\n");
               writer.close();
    	   }
       } 
	}
}
