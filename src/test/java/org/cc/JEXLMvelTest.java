package org.cc;


import org.apache.commons.jexl3.*;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JEXLMvelTest {

    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
    String expression01="ret=(($.G1 + $.G2 + $.G3) * 0.1) + $.G4; true";

    public long test_jxel_expression(){
        JexlScript expr = jexl.createScript(expression01);

        long ts = System.nanoTime();
        JexlContext ctx =new MapContext();
        for(int i=0;i<10000;i++) {
            Map m = new HashMap();
            m.put("G1", 100);
            m.put("G2", 200);
            m.put("G3", 300);
            m.put("G4", 400);
            ctx.set("$",m);
            expr.execute(ctx);
        }
        long te = System.nanoTime();
        //System.out.println("========== jxel ============");
        //System.out.println(ctx.get("ret"));
        return (te-ts);
    }


    public long test_mvel_expression(){
        Serializable expr001 = MVEL.compileExpression(expression01);
        long ts = System.nanoTime();
        Map ctx = new HashMap();
        for(int i=0;i<10000;i++) {
            Map m = new HashMap();
            m.put("G1", 100);
            m.put("G2", 200);
            m.put("G3", 300);
            m.put("G4", 400);
            ctx.put("$",m);
            //MVEL.eval(expression01,ctx);
            MVEL.executeExpression(expr001,ctx);
        }
        long te = System.nanoTime();
        //System.out.println("========== mvel ============");
        //System.out.println(ctx.get("ret"));
        return (te-ts);
    }

    public long test_nat_expression(){
        Serializable expr001 = MVEL.compileExpression(expression01);
        long ts = System.nanoTime();
        Map ctx = new HashMap();
        for(int i=0;i<10000;i++) {
            Map m = new HashMap();
            m.put("G1", 100);
            m.put("G2", 200);
            m.put("G3", 300);
            m.put("G4", 400);
            ctx.put("$",m);
            int G1 =  (int) ((Map)ctx.get("$")).get("G1");
            int G2 =  (int) ((Map)ctx.get("$")).get("G2");
            int G3 =  (int) ((Map)ctx.get("$")).get("G3");
            int G4 =  (int) ((Map)ctx.get("$")).get("G4");
            double ret = (G1+G2+G3)*0.1+G4;
            ctx.put("ret",ret);
        }
        long te = System.nanoTime();
        //System.out.println("========== nat ============");
        //System.out.println(ctx.get("ret"));
        return (te-ts);
    }

    @Test
    public void test_all(){
        long s1=0;
        long s2=0 ;
        long s3=0 ;
        for(int i=0;i<100;i++) {
            s1+=test_jxel_expression();
            s2+=test_mvel_expression();
            s3+=test_nat_expression();
        }
        System.out.println("====== jxel : " + (s1/1E9));
        System.out.println("====== mvel : " + (s2/1E9));
        System.out.println("====== nat : " + (s3/1E9));
    }

}
