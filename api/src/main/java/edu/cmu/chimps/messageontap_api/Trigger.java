/**
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

/**
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
 * + Set<Constraint> contraints
 * <p>
 * + int mood
 * <p>
 * + int direction
 */
public class Trigger {

    public enum Relation {UNKNOWN, CONCATENATION, DIRECT_SUBORDINATION, NESTED_SUBORNIDATION}

    public class Constraint {
        //TODO
        String tagA_name = "";
        String tagB_name = "";
        Relation relation = Relation.UNKNOWN;
    }

    private String mName = "null";

    private Set<Object> mMandatoryTags = new HashSet<>();

    private Set<Object> mOptionalTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private ParseTree.Mood mMood = ParseTree.Mood.UNKNOWN;

    private ParseTree.Direction mDirection = ParseTree.Direction.UNKNOWN;

    private String mPackageName; //TODO: Plugin Id

    public Trigger() {
        mName = "";
        mMandatoryTags = new HashSet<Object>();
        mOptionalTags = new HashSet<Object>();
        mConstraints = new HashSet<Constraint>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
    }

    public Trigger(String name, Set<String> pluginMandatoryTags) {
        this.mName = name;
        mMandatoryTags = new HashSet<Object>();
        mOptionalTags = new HashSet<Object>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        for (String t : pluginMandatoryTags) {
            mMandatoryTags.add(t);
        }
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags) {
        this.mName = name;
        mMandatoryTags = new HashSet<Object>();
        mOptionalTags = new HashSet<Object>();
        mConstraints = new HashSet<Constraint>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        for (String t : pluginOptionalTags) {
            mOptionalTags.add(t);
        }
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints) {
        this.mName = name;
        mMandatoryTags = new HashSet<Object>();
        mOptionalTags = new HashSet<Object>();
        mConstraints = new HashSet<Constraint>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
        for (String t : pluginMandatoryTags) {
            mMandatoryTags.add(t);

        }
        for (String t : pluginOptionalTags) {
            mOptionalTags.add(t);

        }
        this.mConstraints = constraints;
    }

    public Trigger(String name, Set<String> pluginMandatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints
            , ParseTree.Mood mood, ParseTree.Direction direction) {
        this.mName = name;
        mMandatoryTags = new HashSet<Object>();
        mOptionalTags = new HashSet<Object>();
        mConstraints = new HashSet<Constraint>();
        for (String t : pluginMandatoryTags) {
            mMandatoryTags.add(t);

        }
        for (String t : pluginOptionalTags) {
            mOptionalTags.add(t);

        }
        if (constraints != null)
            this.mConstraints = constraints;
        this.mMood = mood;
        this.mDirection = direction;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getJson() {
        return JSONUtils.simpleObjectToJson(this, Globals.TYPE_TRIGGER);
    }

    public String getTypeKey() {
        return Globals.TYPE_TRIGGER;
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

        //Direction Judge
        //Incoming or Outgoing?
        if (mDirection != ParseTree.Direction.UNKNOWN) {
            if (parseTree.direction != mDirection) {
                return false;
            }
        }

        //Mood Judge
        //IMPERATIVE, INTERROGTIVE
        if (mMood != ParseTree.Mood.UNKNOWN) {
            if (parseTree.mood != mMood) {
                return false;
            }
        }

        //Tag Judge Contains

        //Add Tags from ParseTree
        HashMap<Object, Integer> tagNames = new HashMap<>();
        for (int i = 0; i < parseTree.mNodeList.size(); i++) {
            if (!parseTree.getNodeById(i).getTagList().isEmpty()) {
                for (Object t : parseTree.mNodeList.get(i).getTagList()) {
                    tagNames.put(t, i);
                }
            }
        }

        if (!mMandatoryTags.isEmpty()) {
            if (!tagNames.keySet().containsAll(mMandatoryTags)) {
                return false;
            }
        }

        //A -> B
        if (!mConstraints.isEmpty()) {
            for (Constraint c : mConstraints) {
                String tagA = c.tagA_name;
                String tagB = c.tagB_name;
                Relation relation = c.relation;
                ParseTree.Node nodeA = parseTree.mNodeList.get(tagNames.get(tagA));
                ParseTree.Node nodeB = parseTree.mNodeList.get(tagNames.get(tagB));
                if (relation == Relation.CONCATENATION) {
                    if (!parseTree.isConcatenation(nodeA, nodeB)) {
                        return false;
                    }
                } else if (relation == Relation.DIRECT_SUBORDINATION) { // A is B's direct father
                    if (!parseTree.isSubordinate(nodeA, nodeB, false)) {
                        return false;
                    }
                } else if (relation == Relation.NESTED_SUBORNIDATION) { // A is B's ancestor
                    if (!parseTree.isSubordinate(nodeA, nodeB, true)) {
                        return false;
                    }
                }
            }
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

    public Set<Object> getAllTags() {
        Set<Object> result = new HashSet<Object>();
        result.clear();
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
