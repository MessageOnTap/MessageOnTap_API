package edu.cmu.chimps.messageontap_api;

import android.text.TextUtils;

import java.util.Set;
import java.util.regex.Pattern;

public class Tag {

    private String mName;
    private String mEntityName;
    private String mPackageName;
    private Set<String> mRegularExpressions;
    private Integer usageCount;

    public Tag(String name, Set<String> RegularExpressions) {
        this.mName = name;
        this.mRegularExpressions = RegularExpressions;
        this.usageCount = 0;
    }

    public Tag(String name, String entityName) {
        this.mName = name;
        this.mEntityName = entityName;
        this.usageCount = 0;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setKeywordList(Set keywordList) {
        mRegularExpressions = keywordList;
    }

    public Set<String> getKeywordList() {
        return mRegularExpressions;
    }

    public void setEntityName(String entityName) {
        this.mEntityName = entityName;
    }

    public String getEntityName() {
        return mEntityName;
    }

    private boolean matchRE(String word) {
        Set<String> keywordList = getKeywordList();

        for (String str : keywordList) {
            if (Pattern.matches(str, word))
                return true;
        }
        return false;
    }

    private boolean matchEntity(String entity) {
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

    public void increaseUsageCount() {
        usageCount++;
    }

    public void decreaseUsageCount() {
        usageCount--;
    }

    public void resetUsageCount() {
        usageCount = 0;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }
}
