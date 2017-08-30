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

import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Tag {

    public enum Type {MANDATORY, OPTIONAL}

    private String mName;
    private Set<String> mRegularExpressions;
    private Type mType;


    public Tag(String name, Set<String> regularExpressions, Type type) {
        this.mName = name;
        this.mRegularExpressions = regularExpressions;
        this.mType = type;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public Type getType() {
        return mType;
    }

    public void setRegularExpressions(Set<String> regularExpressions) {
        mRegularExpressions = regularExpressions;
    }

    public Set<String> getRegularExpressions() {
        return mRegularExpressions;
    }

    public String toString() {
        return mName + " (" + mType + ')';
    }
}
