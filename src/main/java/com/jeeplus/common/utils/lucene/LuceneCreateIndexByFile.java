package com.jeeplus.common.utils.lucene;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;

public class LuceneCreateIndexByFile {
public static void main(String[] args) {
	try {
		createIndex("C:\\mysofts\\tomcat\\apache-tomcat-7\\webapps\\uploadfiles\\userfiles\\2018-01-02\\我的上传文件-180102100131.docx", "C:\\mysofts\\tomcat\\apache-tomcat-7\\webapps\\luceneindex");
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	/**
	 * 创建索引
	 * @param filePath  文件路径
	 * @param indexDirectory 保存索引的目录
	 * @throws IOException
	 */
	public static void createIndex(String filePath,String indexDirectory) throws IOException {
	    Directory directory = new SimpleFSDirectory(new File(indexDirectory));
	    // 创建一个简单的分词器,可以对数据进行分词
	    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
	    
	    File file=new File(indexDirectory);
	    if (!file.exists()) {
	    	//如果目录索引不存在，则创建目录
	    	 File file2=new File(file.getParent());
	         file2.mkdirs();
	    }
	    
	    //判断索引目录是不是为空，如果是新建索引，如果不是则追加索引
	    File[] files = new File(indexDirectory).listFiles();
	    boolean newCreate = false;
	    if(files==null||files.length==0) {
	    	newCreate = true;
	    }
	    IndexWriter indexWriter = new IndexWriter(directory, analyzer, newCreate,IndexWriter.MaxFieldLength.UNLIMITED);
	    create(filePath, indexWriter);
	    // 关闭索引
	    indexWriter.close();
	}
	
	private static void create(String dataDirectory,IndexWriter indexWriter) {
		// 创建索引实例
        // 第1个参数是Directory,
        // 第2个是分词器,
        // 第3个表示是否是创建, true代表覆盖原先数据, 如果为false为在此基础上面修改,
        // 第4个MaxFieldLength表示对每个Field限制建立分词索引的最大数目，
        // 如果是MaxFieldLength.UNLIMITED，表示长度没有限制;
        // 如果是MaxFieldLength.LIMITED则表示有限制，可以通过IndexWriter对象的setMaxFieldLength（int
        // n）进行指定
        try {
	        	// 获取所有需要建立索引的文件
	            File file = new File(dataDirectory);
                // 文件的完整路径
                String allPath = file.toString();
                String bianma = codeStringPlus(allPath);
                System.out.println("完整路径：" + allPath);
                // 获取文件名称
                String fileName = file.getName();
                // 获取文件后缀名，将其作为文件类型
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length()).toLowerCase();
                // 文件名称
                System.out.println("文件名称：" + fileName);
                // 文件类型
                System.out.println("文件类型：" + fileType);
                Document doc = new Document();
                // String fileCode = FileType.getFileType(file.toString());
                // 查看各个文件的文件头标记的类型
                // System.out.println("fileCode=" + fileCode);
                InputStream in = new FileInputStream(file);
                InputStreamReader reader = null;

                if (fileType != null && !fileType.equals("")) {
                    if (fileType.equals("doc")) {
                        // 获取doc的word文档
                        WordExtractor wordExtractor = new WordExtractor(in);
                        // 创建Field对象，并放入doc对象中
                        // Field的各个字段含义如下：
                        // 第1个参数是设置field的name，
                        // 第2个参数是value，value值可以是文本（String类型，Reader类型或者是预分享的TokenStream）,
                        // 二进制（byet[]）, 或者是数字（一个 Number类型）
                        // 第3个参数是Field.Store，选择是否存储，如果存储的话在检索的时候可以返回值
                        // 第4个参数是Field.Index，用来设置索引方式
                        System.out.println(wordExtractor.getText());
                        doc.add(new Field("contents", wordExtractor.getText(),Field.Store.YES, Field.Index.ANALYZED));
                        // 关闭文档
                        wordExtractor.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");
                    } else if (fileType.equals("docx")) {
                        // 获取docx的word文档
                        XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(new XWPFDocument(in));
                        // 创建Field对象，并放入doc对象中
                        System.out.println(xwpfWordExtractor.getText());
                        doc.add(new Field("contents", xwpfWordExtractor.getText(),Field.Store.YES, Field.Index.ANALYZED));
                        // 关闭文档
                        xwpfWordExtractor.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else if (fileType.equals("ppt")) {
                    	PowerPointExtractor ppt = new PowerPointExtractor(in);
                        System.out.println(ppt.getText());
                        doc.add(new Field("contents", ppt.getText(),Field.Store.YES, Field.Index.ANALYZED));
                        // 关闭文档
                        ppt.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");
                    } else if (fileType.equals("pptx")) {
    					try {
    						XSLFPowerPointExtractor pptx =  new XSLFPowerPointExtractor(POIXMLDocument.openPackage(allPath));
    						System.out.println(pptx.getText());
    						doc.add(new Field("contents", pptx.getText(),Field.Store.YES, Field.Index.ANALYZED));
    						// 关闭文档
    						pptx.close();
    						System.out.println("注意：已为文件“" + fileName + "”创建了索引");
    					} catch (XmlException e) {
    						e.printStackTrace();
    					} catch (OpenXML4JException e) {
    						e.printStackTrace();
    					}
                    }else if (fileType.equals("pdf")) {
                        // 获取pdf文档
                        PDFParser parser = new PDFParser(in);
                        parser.parse();
                        PDDocument pdDocument = parser.getPDDocument();
                        PDFTextStripper stripper = new PDFTextStripper();
                        // 创建Field对象，并放入doc对象中
                        doc.add(new Field("contents", stripper.getText(pdDocument),
                                Field.Store.NO, Field.Index.ANALYZED));
                        // 关闭文档
                        pdDocument.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else if (fileType.equals("txt")) {
                        // 建立一个输入流对象reader  
                        reader = new InputStreamReader(in,bianma); 
                        // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        BufferedReader br = new BufferedReader(reader);   
                        String txtFile = "";
                        String line = null;

                        while ((line = br.readLine()) != null) {  
                            // 一次读入一行数据
                            txtFile += line;   
                        }  
                        // 创建Field对象，并放入doc对象中
                        System.out.println(txtFile);
                        doc.add(new Field("contents", txtFile, Field.Store.NO,Field.Index.ANALYZED));
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else {
                        System.out.println();
                    }

                }
                // 创建文件名的域，并放入doc对象中
                doc.add(new Field("filename", file.getName(), Field.Store.YES,Field.Index.NOT_ANALYZED));
                // 创建时间的域，并放入doc对象中
                doc.add(new Field("indexDate", DateTools.dateToString(new Date(),DateTools.Resolution.DAY), Field.Store.YES,Field.Index.NOT_ANALYZED));
                // 写入IndexWriter
                indexWriter.addDocument(doc);
                // 换行
                System.out.println();
            // 查看IndexWriter里面有多少个索引
            System.out.println("numDocs=" + indexWriter.numDocs());
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
    
    /**
     * 获取文件编码
    * @param fileName
    * @throws Exception
    */
   public static String codeStringPlus(String fileName){  
        BufferedInputStream bin = null;  
        String code = null;  
        try {  
            bin = new BufferedInputStream(new FileInputStream(fileName));  
            int p = (bin.read() << 8) + bin.read();  
            switch (p) {  
            case 0xefbb:  
                code = "UTF-8";  
                break;  
            case 0xfffe:  
                code = "Unicode";  
                break;  
            case 0xfeff:  
                code = "UTF-16BE";  
                break;  
            default:  
                code = "GBK";  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {
				bin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
        return code;  
    } 
}