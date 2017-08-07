/*
 * Copyright 2017 CHIMPS Lab, Carnegie Mellon University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    /**
     * Check whether this tag matches a word using regular expressions
     *
     * @param word the word to be checked
     * @return boolean whether it matches
     */
    private boolean matchRE(String word) {
        Set<String> keywordList = getKeywordList();

        for (String str : keywordList) {
            if (Pattern.matches(str, word))
                return true;
        }
        return false;
    }

    /**
     * Check whether this tag matches a word using entity recognition
     *
     * @param entity the entity to be checked
     * @return boolean whether it matches
     */
    private boolean matchEntity(String entity) {
        return TextUtils.equals(entity, getEntityName());
    }

    /**
     * Check whether this tag matches a word
     *
     * @param word   the word to be checked
     * @param entity the entity of the word to be checked
     * @return boolean whether it matches
     */
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
