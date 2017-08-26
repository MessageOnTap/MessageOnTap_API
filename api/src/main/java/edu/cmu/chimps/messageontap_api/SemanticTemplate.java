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


import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * This is the data type for plugins to set up their triggers at message perception
 * 1. InternalTag List
 */

/**
 * SemanticTemplate
 * <p>
 * + Set<String> mandatoryTags
 * <p>
 * + Set<String> optionalTags
 * <p>
 * + Set<Constraint> constraints
 * <p>
 * + int mood
 * <p>
 * + int direction
 */
@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public class SemanticTemplate {

    private String mName = "null";

    private Set<Tag> mTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private ParseTree.Mood mMood = ParseTree.Mood.UNKNOWN;

    private ParseTree.Direction mDirection = ParseTree.Direction.UNKNOWN;

    public SemanticTemplate() {
        mName = "";
        mTags = new HashSet<>();
        mConstraints = new HashSet<>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
    }

    public String name() {
        return mName;
    }

    public SemanticTemplate name(String name) {
        mName = name;
        return this;
    }

    public Set<Tag> tags() {
        return mTags;
    }

    public SemanticTemplate tags(Set tags) {
        mTags = tags;
        return this;
    }

    public Set<Constraint> constraints() {
        return mConstraints;
    }

    public SemanticTemplate constraints(Set constraints) {
        mConstraints = constraints;
        return this;
    }

    public ParseTree.Mood mood() {
        return mMood;
    }

    public SemanticTemplate mood(ParseTree.Mood mood) {
        mMood = mood;
        return this;
    }

    public ParseTree.Direction direction() {
        return mDirection;
    }

    public SemanticTemplate direction(ParseTree.Direction direction) {
        mDirection = direction;
        return this;
    }

    public String toString() {
        return JSONUtils.simpleObjectToJson(this, new TypeToken<SemanticTemplate>(){}.getType());
    }

    /**
     * Check if the trigger matches a parse tree
     *
     * @param parseTree the parse tree to be checked
     * @return boolean whether it matches or not
     */
    /*public boolean matchTrigger(ParseTree parseTree) {
        // 1. InternalTag List
        // 2. InternalTag Relation
        // 3. Mood? Direction?

        //Direction Check
        //Incoming or Outgoing?
        if (mDirection != ParseTree.Direction.UNKNOWN) {
            if (parseTree.direction != mDirection)
                return false;
        }

        //Mood Check
        //IMPERATIVE, INTERROGATIVE
        if (mMood != ParseTree.Mood.UNKNOWN) {
            if (parseTree.mood != mMood)
                return false;
        }

        //Tags Check

        //Add Tags from ParseTree

        HashMap<Object, Integer> tagNames = new HashMap<>();
        for (int i = parseTree.size() - 1; i > -1; --i) {
            Set tagList = parseTree.getNodeByIndex(i).getTagList();
            if (tagList != null) {
                for (Object t : tagList)
                    tagNames.put(t, i);
            }
        }

        if (!tagNames.keySet().containsAll(mMandatoryTags)) {
            return false;
        }

        //A -> B
        for (Constraint c : mConstraints) {
            String tagA = c.tagA_name;
            String tagB = c.tagB_name;
            Relation relation = c.relation;
            ParseTree.Node nodeA = parseTree.getNodeByIndex(tagNames.get(tagA));
            ParseTree.Node nodeB = parseTree.getNodeByIndex(tagNames.get(tagB));
            Boolean match = false;
            switch (relation) {
                case CONCATENATION:
                    match = parseTree.isConcatenation(nodeA, nodeB);
                    break;
                case DIRECT_SUBORDINATION: // A is B's direct father
                    match = parseTree.isSubordinate(nodeA, nodeB, false);
                    break;
                case NESTED_SUBORDINATION: // A is B's ancestor
                    match = parseTree.isSubordinate(nodeA, nodeB, true);
                    break;
            }
            if (!match)
                return false;
        }

        return true;
    }*/
}
