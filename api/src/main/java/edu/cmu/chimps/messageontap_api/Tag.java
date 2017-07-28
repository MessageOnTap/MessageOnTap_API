package edu.cmu.chimps.messageontap_api;

import android.content.Context;
import android.text.TextUtils;

import com.github.privacystreams.core.Item;
import com.github.privacystreams.utils.annotations.PSItemField;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Tag {
    public static final String TAG_ID = "tag_id";
    public static final String NAME = "name";
    public static final String KEYWORD_LIST = "keyword_list";
    public static final String ENTITY_NAME = "entity_name";

    private String mName;
    private String mEntityName;
    private ArrayList<String> mKeywordList;

    public Tag(String name, ArrayList<String> keywordList){
        this.mName = name;
        this.mKeywordList = keywordList;
    }

    public Tag(String name, String entityName){
        this.mName = name;
        this.mEntityName = entityName;
    }

    void setName(String name){
        mName = name;
    }

    String getName(){
        return mName;
    }

    void setKeywordList(ArrayList keywordList){
        mKeywordList = keywordList;
    }

    ArrayList<String> getKeywordList(){
        return mKeywordList;
    }

    void setEntityName(String entityName){
        this.mEntityName = entityName;
    }

    String getEntityName(){
        return mEntityName;
    }

    boolean matchRE(String word){

        ArrayList<String> keywordList = getKeywordList();
        boolean flag = false;

        for(String str: keywordList){
                if(Pattern.matches(str, word)){
                    flag = true;
                    break;
                };
            }
        return flag;
    }

    boolean matchEntity(String entity){
        return TextUtils.equals(entity, getEntityName());
    }

    public boolean matchWord(String word, String entity){
        if(getKeywordList()!=null)
            if(matchRE(word))
                return true;
        if(getEntityName() != null)
            if(getEntityName() != null && matchEntity(entity))
                return true;

        return false;
    }

}
