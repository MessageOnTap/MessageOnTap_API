package edu.cmu.chimps.messageontap_api;

import android.text.TextUtils;

import java.util.Set;
import java.util.regex.Pattern;

public class Tag {
    public static final String TAG_ID = "tag_id";
    public static final String NAME = "name";
    public static final String KEYWORD_LIST = "keyword_list";
    public static final String ENTITY_NAME = "entity_name";

    private String mName;
    private String mEntityName;
    private Set<String> mKeywordList;

    public Tag(String name, Set<String> keywordList) {
        this.mName = name;
        this.mKeywordList = keywordList;
    }

    public Tag(String name, String entityName) {
        this.mName = name;
        this.mEntityName = entityName;
    }

    void setName(String name) {
        mName = name;
    }

    String getName() {
        return mName;
    }

    void setKeywordList(Set keywordList) {
        mKeywordList = keywordList;
    }

    Set<String> getKeywordList() {
        return mKeywordList;
    }

    void setEntityName(String entityName) {
        this.mEntityName = entityName;
    }

    String getEntityName() {
        return mEntityName;
    }

    boolean matchRE(String word) {
        Set<String> keywordList = getKeywordList();

        for (String str : keywordList) {
            if (Pattern.matches(str, word))
                return true;
        }
        return false;
    }

    boolean matchEntity(String entity) {
        return TextUtils.equals(entity, getEntityName());
    }

    public boolean matchWord(String word, String entity) {
        if (getKeywordList() != null)
            if (matchRE(word))
                return true;
        if (getEntityName() != null)
            if (getEntityName() != null && matchEntity(entity))
                return true;

        return false;
    }

}
