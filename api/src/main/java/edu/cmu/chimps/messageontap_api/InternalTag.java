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

@SuppressWarnings({"unused", "WeakerAccess"})
public class InternalTag { // Used internally. Plugin developers should use Tag instead.

    private String mName;
    private String mEntityName;
    private String mPackageName;
    private Set<String> mRegularExpressions;
    private Integer usageCount;

    public InternalTag(String name, Set<String> regularExpressions) {
        String ENTITY_INTERNAL_KEY = "MessageOnTap_entity_INTERNAL_vimIsTheBestLOLbyAdamYi_hahaha";
        this.mName = name;
        Boolean isCommonTag = false;
        String entity = null;
        if (regularExpressions.size() == 2) {
            for (String re : regularExpressions) {
                if (TextUtils.equals(re, ENTITY_INTERNAL_KEY))
                    isCommonTag = true;
                else
                    entity = re;
            }
        }
        if (isCommonTag && entity != null)
            this.mEntityName = entity;
        else
            this.mRegularExpressions = regularExpressions;
        this.usageCount = 0;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setRegularExpressions(Set<String> regularExpressions) {
        mRegularExpressions = regularExpressions;
    }

    public Set<String> getRegularExpressions() {
        return mRegularExpressions;
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
        for (String str : mRegularExpressions) {
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
        if (getRegularExpressions() != null)
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

    public String toString() {
        return JSONUtils.simpleObjectToJson(this, JSONUtils.TYPE_TAG);
    }
}
