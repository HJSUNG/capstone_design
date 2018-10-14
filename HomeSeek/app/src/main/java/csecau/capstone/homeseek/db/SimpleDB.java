package com.example.dovicvi.home_test.db;

import com.example.dovicvi.home_test.vo.ArticleVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleDB {
    private  static Map<String , ArticleVO> db= new HashMap<String, ArticleVO>();

    public  static  void addArticle(String index, ArticleVO articleVO) {
        db.put(index, articleVO);
    }

    public static ArticleVO getArticle(String index) {
        return db.get(index);
    }

    public static List<String> getIndexes() {
        Iterator<String> keys = db.keySet().iterator();

        List<String> keyList = new ArrayList<String>();
        String key = "";
        while (keys.hasNext() ) {
            key = keys.next();
            keyList.add(key);
        }

        return  keyList;
    }
}
