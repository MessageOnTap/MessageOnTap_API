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


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
 * This is the data type for plugins to set up their triggers at message perception
 * 1. Tag List
 */

/**
 * Trigger
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
public class Trigger {

    public enum Relation {UNKNOWN, CONCATENATION, DIRECT_SUBORDINATION, NESTED_SUBORDINATION}

    public static class Constraint {
        private String tagA_name = "";
        private String tagB_name = "";
        private Relation relation = Relation.UNKNOWN;

        public Constraint(String tagA_name, String tagB_name, Relation relation) {
            this.tagA_name = tagA_name;
            this.tagB_name = tagB_name;
            this.relation = relation;
        }

        public String getTagA() {
            return tagA_name;
        }

        public String getTagB() {
            return tagB_name;
        }

        public Relation getRelation() {
            return relation;
        }
    }

    private String mName = "null";

    private Set<Object> mMandatoryTags = new HashSet<>();

    private Set<Object> mOptionalTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private ParseTree.Mood mMood = ParseTree.Mood.UNKNOWN;

    private ParseTree.Direction mDirection = ParseTree.Direction.UNKNOWN;

    private String mPackageName;

    public Trigger() {
        mName = "";
        mMandatoryTags = new HashSet<>();
        mOptionalTags = new HashSet<>();
        mConstraints = new HashSet<>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
    }

    public Trigger(String name, Set<String> pluginMandatoryTags) {
        this.mName = name;
        mMandatoryTags = new HashSet<>();
        mOptionalTags = new HashSet<>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        if (pluginMandatoryTags != null) {
            mMandatoryTags.addAll(pluginMandatoryTags);
        }
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags) {
        this.mName = name;
        mMandatoryTags = new HashSet<>();
        mOptionalTags = new HashSet<>();
        mConstraints = new HashSet<>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        if (pluginMandatoryTags != null) {
            mMandatoryTags.addAll(pluginMandatoryTags);
        }
        if (pluginOptionalTags != null) {
            mOptionalTags.addAll(pluginOptionalTags);
        }
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints) {
        this.mName = name;
        mMandatoryTags = new HashSet<>();
        mOptionalTags = new HashSet<>();
        mConstraints = new HashSet<>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        if (pluginMandatoryTags != null)
            mMandatoryTags.addAll(pluginMandatoryTags);
        if (pluginOptionalTags != null)
            mOptionalTags.addAll(pluginOptionalTags);
        this.mConstraints = constraints;
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints
            , ParseTree.Mood mood, ParseTree.Direction direction) {
        this.mName = name;
        mMandatoryTags = new HashSet<>();
        mOptionalTags = new HashSet<>();
        mConstraints = new HashSet<>();
        if (pluginMandatoryTags != null)
            mMandatoryTags.addAll(pluginMandatoryTags);
        if (pluginOptionalTags != null)
            mOptionalTags.addAll(pluginOptionalTags);
        if (constraints != null)
            this.mConstraints = constraints;
        this.mMood = mood;
        this.mDirection = direction;
    }

    public String getPackageName() {
        return mPackageName;
    }

    /**
     * Set the packageName the trigger belongs to (this should only be called by PMS, but never by plugins)
     *
     * @param packageName the packageName to be set
     */
    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String toString() {
        return JSONUtils.simpleObjectToJson(this, JSONUtils.TYPE_TRIGGER);
    }

    /**
     * Check if the trigger matches a parse tree
     *
     * @param parseTree the parse tree to be checked
     * @return boolean whether it matches or not
     */
    public boolean matchTrigger(ParseTree parseTree) {
        // 1. Tag List
        // 2. Tag Relation
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
    }

    public Set<Object> getMandatoryTags() {
        return mMandatoryTags;
    }

    public void setMandatoryTags(Set<Object> mandatoryTags) {
        this.mMandatoryTags = mandatoryTags;
    }

    public Set<Object> getOptionalTags() {
        return mOptionalTags;
    }

    public void setOptionalTags(Set<Object> optionalTags) {
        this.mOptionalTags = optionalTags;
    }

    /**
     * Get a set of all (mandatory + optional) tags of the trigger
     *
     * @return the set of all tags
     */
    public Set<Object> getAllTags() {
        Set<Object> result = new HashSet<>();
        result.addAll(mMandatoryTags);
        result.addAll(mOptionalTags);
        return result;
    }

    public Set<Constraint> getConstraints() {
        return mConstraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.mConstraints = constraints;
    }
}
