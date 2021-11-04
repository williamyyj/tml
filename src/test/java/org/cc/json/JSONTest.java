package org.cc.json;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class JSONTest {

    private static String line = "{a:'124',b:456,c:[1,2,3,4,5,6],'d':'中文'}";

    @Test
    public void test_jo001() throws ParseException {
        JSONParser p = new JSONParser(line);

        Map m = p.object();
        String ret = JSON.toString(m);
        System.out.println(ret);
    }

    public long test_parser_javacc() throws ParseException {
        long ts = System.nanoTime();
        JSONParser p = new JSONParser(line);
        //ByteArrayInputStream stream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
        //p.ReInit(stream,"UTF-8");
        p.object();
        long te = System.nanoTime();
        //System.out.println(m);
        return te-ts;
    }


    public long test_parser_org_json() throws ParseException {
        long ts = System.nanoTime();
        JSONObject jo = new JSONObject(line);
        long te = System.nanoTime();
        //System.out.println(jo);
        return te-ts;
    }


    @Test
    public void test_parser() throws Exception {
        long s1 = 0L;
        long s2 = 0L;
        for(int i=0;i<100000;i++){
            s2 += test_parser_org_json();
            s1 += test_parser_javacc();

        }
        List list;

        System.out.println("===== javacc :"+s1/1E9);
        System.out.println("===== org.json :"+s2/1E9);
    }



}
