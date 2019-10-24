package proj7;
import java.io.*;
import java.util.*;

public class HuffmanEncodingDecoding 
{
	static class treeNode
	{
		String chStr;
		int prob;
		String code;
		treeNode next;
		treeNode left;
		treeNode right;
		
		treeNode()
		{
			chStr = "dummy";
			prob = 0;
			next = null;
			left = null;
			right = null;
			code = "None";
			
		}
		
		treeNode(String u, int v, String w, treeNode x, treeNode y,treeNode z)
		{
			chStr=u;
			prob=v;
			code=w;
			next=x;
			left=y;
			right =z;

		}
		
		public static void printNode(treeNode T, BufferedWriter writer) throws Exception
		{
			
			String x;
			x = "chStr: " + T.chStr+ " Prob: " + T.prob;
			writer.write(x);
			if(T.next==null)
			{
				x= " Next:NULL";
				writer.write(x);
			}
			else
			{
				x=" Next:"+T.next.chStr;
				writer.write(x);
			}
			
			if(T.left==null)
			{
				x= " Left:NULL";
				writer.write(x);
			}
			else
			{
				x=" Left:"+T.left.chStr;
				writer.write(x);
			}
			
			if(T.right==null)
			{
				x= " Right:NULL";
				writer.write(x);
				writer.newLine();
			}
			else
			{
				x=" Right:"+T.right.chStr;
				writer.write(x);
				writer.newLine();
			}
			
			
			
		}
		
	}
	
	static class linkedList
	{
		treeNode listHead;
		linkedList()
		{
			listHead=new treeNode();
		}
		
		public static void insertNewNode(treeNode listHead, treeNode newNode)
		{
			treeNode spot = findSpot(listHead,newNode);
			newNode.next = spot.next;
			spot.next = newNode;
		}
		

		public static treeNode findSpot(treeNode listHead, treeNode newNode)
		{
			treeNode spot = listHead;
			while((spot.next!=null) && (spot.next.prob<=newNode.prob))
			{
				if(spot.next.prob == newNode.prob)
				{
					return spot;
				}
				spot = spot.next;
			}
			return spot;
		}
		
		public static void printList(BufferedWriter writer1, treeNode listHead) throws Exception
		{
			treeNode element = listHead;
			String x = "ListHead-->(\""+listHead.chStr+ "\","+ listHead.prob+ "\"";
			
			writer1.write(x);
			element = element.next;
			x="\""+ element.chStr+"\")";
			writer1.write(x);
			
			while(element.next!=null)
			{
				x = "-->(\"" + element.chStr+"\""+","+ element.prob+",";
				writer1.write(x);
				element = element.next;
				x= "\"" +element.chStr+"\")";	
				writer1.write(x);
			}
			x = "-->(\""+ element.chStr +"\""+ "," + element.prob +",NULL)";
			writer1.write(x);
			x ="-->NULL";
			writer1.write(x);
			writer1.newLine();
			
		}
		
		
	}
	
	static class HuffmanBinaryTree
	{
		static treeNode root;
		static String charCode[] = new String[256];
		
		HuffmanBinaryTree()
		{
			root = null;
		}
		
		HuffmanBinaryTree(treeNode T)
		{
			root = T;
		}
		
		public static treeNode constructHuffmanList(int[] a, BufferedWriter out) throws Exception
		{
			String chr;
			int prob;
			linkedList LL = new linkedList();
			
			int i = 0;
			while(i<256)
			{
				if(i==10|| i==13|| i==32){
					i++;
					}
				char letter;
				letter = (char) i;
				chr = Character.toString(letter);
				prob = a[i];
				
				if(prob>0)
				{
					treeNode newNode = new treeNode(chr,prob,"",null,null,null);
					LL.insertNewNode(LL.listHead, newNode);
					LL.printList(out, LL.listHead);
					
				}
				i++;
			}
			
			return LL.listHead;
		}
		
		public treeNode constructHuffmanBinTree(treeNode listHead, BufferedWriter x) throws Exception
		{
			while( listHead.next.next!=null)
			{
				treeNode newNode = new treeNode(); 
				newNode.prob = listHead.next.prob + listHead.next.next.prob;
				newNode.chStr= listHead.next.chStr+listHead.next.next.chStr;
				newNode.left = listHead.next;
				newNode.right = listHead.next.next;
				newNode.next=null;
				
				linkedList.insertNewNode(listHead,newNode);
				listHead.next = listHead.next.next.next;
				linkedList.printList(x, listHead);
			}
			root=listHead.next;
			return root;
		}
		
		public static void preOrderTraversal(treeNode T,BufferedWriter outFile)throws Exception
		{
			if(isLeaf(T))
			{
				treeNode.printNode(T, outFile);
			}
			else
			{
				treeNode.printNode(T,outFile);
				preOrderTraversal(T.left, outFile);
				preOrderTraversal(T.right,outFile);			
			}

			
		}
		
		public static void inOrderTraversal(treeNode T,BufferedWriter outFile)throws Exception
		{
			if(isLeaf(T))
			{
				treeNode.printNode(T, outFile);
			}
			else
			{
				inOrderTraversal(T.left, outFile);
				treeNode.printNode(T,outFile);
				inOrderTraversal(T.right,outFile);			
			}

			
		}
		
		public static void postOrderTraversal(treeNode T,BufferedWriter outFile)throws Exception
		{
			if(isLeaf(T))
			{
				treeNode.printNode(T, outFile);
			}
			else
			{
				postOrderTraversal(T.left, outFile);
				postOrderTraversal(T.right,outFile);
				treeNode.printNode(T,outFile);
			}

			
		}
		
		public static void constructCharCode(treeNode T, String code) 
		{	
			if(isLeaf(T))
			{
				T.code=code;
				char cc = (T.chStr).charAt(0);
				int index = (int) cc;
				charCode[index] = code;
				
			}
			else
			{
				constructCharCode(T.left, code+ "0");
				constructCharCode(T.right, code + "1");
			}
			
			
			
		}
		public static boolean isLeaf(treeNode node)
		{
			if( (node.left==null)&&(node.right==null))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public static void encode(File inFile, File outFile) throws Exception
		{
			BufferedReader one = new BufferedReader(new FileReader(inFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			char charIn;
			int cI;
			while((cI =one.read())!= -1)
			{
				
				charIn = (char) cI;
				int index = (int) charIn; 
				String code = charCode[index];
				if(code!= null)
				{
					writer.write(code);
					
				}
				
			}
			one.close();
			writer.close();
			
			
			
		}
		
		public static void decode(File File, File oFile) throws Exception
		{
			BufferedWriter outFile = new BufferedWriter(new FileWriter(oFile));
			BufferedReader onebit = new BufferedReader(new FileReader(File));
			char cbit;
			treeNode spot = root;
			int cb;
			spot = root;
			try
			{
				while((cb =onebit.read())!= -1)
				{
					cbit = (char)cb;
					if(isLeaf(spot))
					{
						if(spot.chStr== " ")
						{
							outFile.write(" ");
						}
						outFile.write(spot.chStr);
						spot = root;
					}
					
					if(cbit =='0')
					{
						spot = spot.left;
						
					}
					else if(cbit == '1')
					{
						spot = spot.right;
					}
				}
				
			}
			catch(Exception e)
			{
				System.out.print("Error! The compress file contains invalid character!");

			}
			onebit.close();
			outFile.close();
			if(!isLeaf(spot))
			{
				throw new Exception("Error: The compress file is corrupted!");
			}

		}
		
		public static void userInterface() throws Exception
		{
			String nameOrg;
			String nameCompress;
			String nameDecompress;
			char yesNo;
			
			Scanner in = new Scanner(System.in);
			
			System.out.println("Do you want to encode a file");
			yesNo = (in.next()).charAt(0);
			
			while(yesNo != 'N')
			{
				System.out.println("What is the name of the File");
				nameOrg = in.next();
				String nOrg = nameOrg;
				nameOrg =nameOrg.split("\\.")[0];
				
				nameCompress = nameOrg;
				nameCompress += " _Compress";
				
				nameDecompress = nameOrg;
				nameDecompress+= "_Decompress";
				
				File compFile = new File(nameCompress);
				File deCompFile = new File(nameDecompress);
				
				File orgFile = new File(nOrg);
				encode(orgFile,compFile);
				
				
				decode(compFile,deCompFile);
					
				
			}
			
		}
	}
	
	
	
	static class HuffmanEncoding
	{
		
		public static void printAry(int[] a , BufferedWriter b) throws IOException
		{
			String x="";
			b.newLine();
			for(int i =0; i<a.length; i++)
			{
				if((a[i] != 0))
				{
					if(i==10|| i==13|| i==32){continue;}
						x= (char) i + " "+(int)a[i];
						b.write(x);
						b.newLine();			
				}		
			}
			
		}
		
		
		private static int[] computCharCounts(FileInputStream inFile) throws Exception
		{
			int[] charCounts = new int[256];
			char charIn;
			int index,x=0;
			//step 4: repeat 1-3 while file not finished.I used a variable 'x' to check for EOF  
			while((x=inFile.read()) != -1){
				//step 1: get next char from file
				charIn=(char)x ;
				//step 2: cast charIn to integer
				index = (int) charIn;
				//step 3: increment array
				charCounts[index]++;
			}
			return charCounts;

		}
	}
			
			
		
		public static void main(String[] argc)throws Exception 
		{

			
	    	FileInputStream inFile = new FileInputStream(argc[0]);
	    	String debug;
	    	debug = argc[0];
	    	debug=debug.substring(0,28)+debug.substring(29);
	    	debug+= "_Debug";
	    	File Debug = new File(debug);
	    	BufferedWriter writer5 = new BufferedWriter(new FileWriter(Debug));
	    	String wr;
	    	HuffmanEncoding he = new HuffmanEncoding();
	    	int[] cc;
	    	
	    	wr = "Character counts";
	    	writer5.write(wr);
	    	
	    	cc = he.computCharCounts(inFile);
	    	he.printAry(cc,writer5);
	    	writer5.newLine();
	    	
	    	HuffmanBinaryTree HBT = new HuffmanBinaryTree();
	    	wr = "Linked List";
	    	writer5.newLine();
	    	writer5.write(wr);
	    	writer5.newLine();
	    	treeNode x = HBT.constructHuffmanList(cc,writer5);
	    	treeNode root = HBT.constructHuffmanBinTree(x, writer5);
	    	writer5.newLine();
	    	
	    	
	    	writer5.newLine();
	    	wr = "PreOrder";
	    	writer5.write(wr);
	    	writer5.newLine();
	    	HBT.preOrderTraversal(root, writer5);
	    	writer5.newLine();
	    	
	    
	    	wr="InOrder";
	    	writer5.newLine();
	    	writer5.write(wr);
	    	writer5.newLine();
	    	HBT.inOrderTraversal(root, writer5);
	    	writer5.newLine();
	    	
	    	
	    	wr="PostOrder";
	    	writer5.newLine();
	    	writer5.write(wr);
	    	writer5.newLine();
	    	HBT.postOrderTraversal(root, writer5);
	    	writer5.newLine();
	    	
	    	writer5.write("Char code");
	    	writer5.newLine();
	   
			
	    	String [] charc = new String[256];
	    		    	
	    	HBT.constructCharCode(root, "");
	    	
	    	charc = HBT.charCode;
	    	
  	
	    	writer5.close();
			inFile.close();
			
			HBT.userInterface();
			
		//	BufferedWriter writer6=new BufferedWriter( new FileWriter(encodeFile));
			
			
			

		}


	}


