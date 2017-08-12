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
import android.util.LongSparseArray;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class ParseTree {

    public static final int NOT_EXIST = -1;

    /*
     * Universal POS tags by Stanford
     */
    public static final String POS_ADJECTIVE = "ADJ";
    public static final String POS_ADVERB = "ADV";
    public static final String POS_VERB = "VERB";

    public static final String POS_NUMERAL = "NUM";
    public static final String POS_NOUN = "NOUN";
    public static final String POS_PROPERNOUN = "PROPN";
    public static final String POS_PRONOUN = "PRON";

    public static final String POS_COORDINATING_CONJUNCTION = "CCONJ";
    public static final String POS_SUBORDINATING_CONJUNCTION = "SCONJ";

    public static final String POS_ADPOSITION = "ADP";
    public static final String POS_AUXILIARY = "AUX";
    public static final String POS_DETERMINER = "DET";
    public static final String POS_INTERJECTION = "INTJ";

    public static final String POS_PARTICLE = "PART";
    public static final String POS_PUNCTUAATION = "PUNCT";
    public static final String POS_SYMBOL = "SYM";

    public static final String POS_UNKNOWN = "X";

    /*
     * Universal Dependencies
     */
    public static final String DEP_NOUN_SUBJECT = "NSUBJ";
    public static final String DEP_OBJECTIVE = "OBJ";
    public static final String DEP_INDIRECT_OBJECTIVE = "IOBJ"; // Tom teaches Sam (direct obj) math (indirect obj)

    public static final String DEP_CLAUSE_SUBJECT = "CSUBJ";
    public static final String DEP_CLAUSAL_COMPLEMENT = "CCOMP"; // this one has its own subject. eg. Adam says that Mars likes to swim.
    public static final String DEP_OPEN_CLAUSAL_COMPLEMENT = "XCOMP"; // this one does not. eg Fanglin looks great.

    public static final String DEP_OBLIQUE_NOMINAL = "OBL";
    public static final String DEP_VOCATIVE = "VOCATIVE";
    public static final String DEP_EXPLETIVE = "EXPL";
    public static final String DEP_DISLOCATED = "DISLOCATED";

    public static final String DEP_AUXILIARY = "AUX";
    public static final String DEP_COPULA = "COP";
    public static final String DEP_MARKER = "MARK";

    public static final String DEP_ADVERB_CLAUSE_MODIFIER = "ADVCL";
    public static final String DEP_ADVERB_MODIFIER = "ADVMOB";
    public static final String DEP_NOMINAL_MODIFIER = "NMOD";
    public static final String DEP_APPOSITIONAL_MODIFIER = "APPOS";
    public static final String DEP_NUMERIC_MODIFIER = "NUMMOD";
    public static final String DEP_CLAUSAL_MODIFIER = "ACL";
    public static final String DEP_ADJECTIVE_MODIFIER = "AMOD";

    public static final String DEP_DISCOURSE = "DISCOURSE";
    public static final String DEP_DETERMINER = "DET";
    public static final String DEP_CLASSIFIER = "CLF";
    public static final String DEP_CASE = "CASE";

    public static final String DEP_CONJUNCTION = "CONJ";
    public static final String DEP_COORDINATING_CONJUNCTION = "CC";
    public static final String DEP_NOUN_NOUN = "NN";

    public static final String DEP_FIXED_MULTIWORD_EXPRESSION = "FIXED";
    public static final String DEP_FLAT_MULTIWORDD_EXPRESSION = "FLAT";
    public static final String DEP_COMPOUND = "COMPOUND";

    public static final String DEP_LIST = "LIST";
    public static final String DEP_PARATAXIS = "PARATAXIS";

    public static final String DEP_ORPHAN = "ORPHAN";
    public static final String DEP_GOES_WITH = "GOESWITH";
    public static final String DEP_REPARANDUM = "REPARANDUM"; //overridden disfluency

    public static final String DEP_PUNCTUATION = "PUNCT";
    public static final String DEP_ROOT = "ROOT";
    public static final String DEP_UNKNOWN = "DEP";

    public enum Mood {UNKNOWN, IMPERATIVE, INTERROGATIVE}

    public enum Direction {UNKNOWN, INCOMING, OUTGOING}

    public enum Flag {NORMAL, DELETE, MERGE}

    /*
     * Node of ParseTree
     * Variables:                                                Functions:
     * - mId                                                     + GETTER & SETTER
     * - mType                                                   + GETTER & SETTER
     * - mWord                                                   + GETTER & SETTER
     * - mEntity                                                 + GETTER & SETTER
     * - mChildrenIds                                            + GETTER & SETTER
     * - mParentId                                               + GETTER & SETTER
     * - mRelation                                               + GETTER & SETTER
     * - mFlag                                                   + GETTER & SETTER
     * - mTagList                                                + GETTER & SETTER
     * + toString()
     * + print()
     */
    // START Node Class
    static public class Node {

        private int mId;
        private String mType;
        private String mWord;
        private String mEntity;
        private Set<Integer> mChildrenIds;
        private int mParentId;
        private String mRelation;
        private Flag mFlag; // 0 = normal, 1 = delete, 2 = merge
        private Set<Object> mTagSet;

        public Node() {
            this.mChildrenIds = new HashSet<>();
            this.mTagSet = new HashSet<>();
            this.mFlag = Flag.DELETE;
        }

        public Node(Node another) {
            this.mId = another.mId;
            this.mType = another.mType;
            this.mWord = another.mWord;
            this.mEntity = another.mEntity;
            this.mChildrenIds = new HashSet<>(another.mChildrenIds);
            this.mParentId = another.mParentId;
            this.mRelation = another.mRelation;
            this.mFlag = another.mFlag;
            this.mTagSet = new HashSet<>(another.mTagSet);
        }

        public void setValue(String type, String word) {
            this.mType = type;
            this.mWord = word;
            //this.entity = null;
            //this.parentId = null;
            //this.childrenIds = new ArrayList<>();
            this.mFlag = Flag.NORMAL;
        }

        public void setId(int id) {
            this.mId = id;
        }

        public int getId() {
            return this.mId;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public String getWord() {
            return mWord;
        }

        public void setWord(String word) {
            this.mWord = word;
        }

        public String getEntity() {
            return mEntity;
        }

        public void setEntity(String entity) {
            this.mEntity = entity;
        }

        public Set<Integer> getChildrenIds() {
            return mChildrenIds;
        }

        public void addChildrenId(int id) {
            mChildrenIds.add(id);
        }

        public void setChildrenIds(Set<Integer> childrenIds) {
            this.mChildrenIds = childrenIds;
        }

        public int getParentId() {
            return mParentId;
        }

        public void setParentId(int parentId) {
            this.mParentId = parentId;
        }

        public String getRelation() {
            return mRelation;
        }

        public void setRelation(String relation) {
            this.mRelation = relation;
        }

        public Flag getFlag() {
            return mFlag;
        }

        public void setFlag(Flag flag) {
            this.mFlag = flag;
        }

        public Set<Object> getTagList() {
            return mTagSet;
        }

        public void setTagList(Set tagSet) {
            mTagSet = new HashSet<>();
            if (tagSet != null)
                mTagSet.addAll(tagSet);
        }

        public void addTag(String t) {
            mTagSet.add(t);
        }

        public void addTag(Long t) {
            mTagSet.add(t);
        }

        public boolean isRoot() {
            return mParentId == NOT_EXIST;
        }

    }
    // END Node Class

    SparseArray<Node> mNodeList;
    int mRootId;
    public long[] time;

    public Mood mood;
    public Direction direction;

    public ParseTree() {
        this.mNodeList = new SparseArray<>();
        this.time = new long[2];
    }

    public ParseTree(ParseTree another) {
        this.mNodeList = new SparseArray<>();
        if (another.mNodeList != null) {
            for (int i = 0; i < another.mNodeList.size(); i++) {
                mNodeList.put(another.mNodeList.keyAt(i),
                        new Node(another.mNodeList.valueAt(i)));
            }
        }
        this.mRootId = another.mRootId;
        if (another.time != null)
            this.time = another.time.clone();
        else
            this.time = new long[2];
        this.mood = another.mood;
        this.direction = another.direction;
    }

    public void setNodeById(int id, Node node) {
        node.setId(id);
        mNodeList.put(id, node);
    }

    public void setNodeWordById(int id, String word) {
        mNodeList.get(id).setWord(word);
    }

    public void setNodeParentId(int nodeId, int parentId) {
        mNodeList.get(nodeId).setParentId(parentId);
    }

    public void addChildById(int nodeId, int childId) {
        mNodeList.get(nodeId).addChildrenId(childId);
    }

    public void removeChildrenById(int nodeId, ArrayList<Integer> removeChildrenIdList) {
        mNodeList.get(nodeId).getChildrenIds().removeAll(removeChildrenIdList);
    }

    private void addChildreById(int nodeId, ArrayList<Integer> addChildrenList) {
        mNodeList.get(nodeId).getChildrenIds().addAll(addChildrenList);
    }

    private void setNodeTypeById(int id, String type) {
        mNodeList.get(id).setType(type);
    }

    private void setNodeFlagById(int nodeId, Flag flag) {
        mNodeList.get(nodeId).setFlag(flag);
    }

    private void setRelationById(int advmodNodeId, String relation) {
        mNodeList.get(advmodNodeId).setRelation(relation);
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Node getRoot() {
        return mNodeList.get(mRootId);
    }

    /**
     * Check if two nodes share the same parent
     *
     * @param a a node to be checked
     * @param b a node to be checked
     * @return boolean whether they share the same parent
     */
    public boolean isConcatenation(Node a, Node b) {
        return a.getParentId() == b.getParentId();
    }

    /**
     * Check if one node is subordinate to another.
     *
     * @param a      the Node that is checked to be the father/ancestor of b
     * @param b      a Node that is checked to be subordinate to a
     * @param nested only allow the situation that b is the direct child
     *               of a when set to false
     * @return boolean whether b is subordinate to a
     */
    public boolean isSubordinate(Node a, Node b, Boolean nested) {
        int parentId = b.getParentId(),
                aId = a.getId();
        if (parentId == aId)
            return true;
        while (nested) {
            b = getNodeById(parentId);
            parentId = b.getParentId();
            if (parentId == NOT_EXIST)
                return false;
            if (parentId == aId)
                return true;
        }
        return false;
    }

    /**
     * Analyze the tree and detect its mood.
     */
    public void moodDetection() {
        for (int i = 0; i < mNodeList.size(); i++) {
            Node node = mNodeList.valueAt(i);
            if (TextUtils.equals(node.getType(), DEP_PUNCTUATION)
                    && TextUtils.equals(node.getWord(), "?")) {
                mood = Mood.INTERROGATIVE;
                return;
            }
            if (TextUtils.equals(getRoot().getType(), POS_VERB)) {
                for (int childId : getRoot().getChildrenIds()) {
                    if (TextUtils.equals(getNodeById(childId).getType(), DEP_NOUN_SUBJECT)) {
                        mood = Mood.INTERROGATIVE;
                        return;
                    }
                }
                mood = Mood.IMPERATIVE;
                return;
            }
        }
        mood = Mood.UNKNOWN;

    }

    /**
     * Merge the Nodes in the ParseTree
     * Example: Let's meet at Hilton Garden Inn
     * [Hilton Garden Inn] Should be merged
     */
    public void merge() {
        merge(getRoot());
        clearMergedNodes(getRoot());
    }

    /**
     * @param node : Start From Root Node
     *             Post-order Traversal Recursive Function
     */
    public void merge(Node node) {

        if (node.getChildrenIds() != null) {
            for (int childId : node.getChildrenIds()) {
                Node child = mNodeList.get(childId);
                merge(child);
                if (child.getFlag() == Flag.MERGE) {
                    if (child.getId() < node.getId())
                        node.setWord(child.getWord() + " " + node.getWord());
                    else
                        node.setWord(node.getWord() + " " + child.getWord());
                    child.setFlag(Flag.DELETE);
                }
            }
        }
    }

    /**
     * Clear the Merged Nodes in the Tree
     *
     * @param node : start From Root Node
     */
    public void clearMergedNodes(Node node) {
        if (node.getChildrenIds() != null) {
            for (int childId : node.getChildrenIds()) {
                Node child = mNodeList.get(childId);
                clearMergedNodes(child);
                if (child.getFlag() == Flag.DELETE) {
                    node.getChildrenIds().remove(childId);
                }
            }
        }
    }


    public boolean isMerge(ArrayList<Node> mNodeList) {
        if (mNodeList == null) return false;
        for (Node n : mNodeList) {
            if (n.getFlag() == Flag.MERGE)
                return true;
        }
        return false;
    }

    public Node getNodeById(int id) {
        return mNodeList.get(id);
    }

    public void setNodeById() {

    }

    /**
     * Reduce the tree to keep only essential data
     * This should not be called directly, but
     * instead called from the other reduce function.
     */
    private void recursiveReduce(int nodeId) {
        if (getNodeById(nodeId).getChildrenIds() != null) {
            ArrayList<Integer> toDelete = new ArrayList<>();
            ArrayList<Integer> toAdd = new ArrayList<>();
            int subjNodeId = NOT_EXIST;
            int dobjNodeId = NOT_EXIST;
            int iobjNodeId = NOT_EXIST;
            for (int childId : getNodeById(nodeId).getChildrenIds()) {
                recursiveReduce(childId);
                switch (getNodeById(childId).getFlag()) {
                    case MERGE:
                        setNodeWordById(nodeId, getNodeById(childId).getWord() + " " + getNodeById(nodeId).getWord());   //???TODO: Or node.getWord() + child.getWord()
                    case DELETE:
                        if (getNodeById(childId).getChildrenIds() != null) {
                            for (int ccId : getNodeById(childId).getChildrenIds()) {
                                setNodeParentId(ccId, nodeId);
                                toAdd.add(ccId);
                            }
                        }
                        toDelete.add(childId);
                        mNodeList.remove(childId);
                        continue;
                }
                /* TODO check the relationship between Subjects (getRelation == "nsubj")
                   TODO and Objects (dobj and pobj)
                 */
                if (getNodeById(childId).getRelation().equals(DEP_NOUN_SUBJECT))
                    subjNodeId = childId;
                if (getNodeById(childId).getRelation().equals(DEP_OBJECTIVE))
                    dobjNodeId = childId;
                if (getNodeById(childId).getRelation().equals(DEP_INDIRECT_OBJECTIVE))
                    iobjNodeId = childId;
            }
            if (subjNodeId != NOT_EXIST) {
                if (dobjNodeId != NOT_EXIST) {
                    setNodeParentId(subjNodeId, dobjNodeId);
                    addChildById(dobjNodeId, subjNodeId);
                    toDelete.add(subjNodeId);
                } else if (iobjNodeId != NOT_EXIST) {
                    setNodeParentId(subjNodeId, iobjNodeId);
                    addChildById(iobjNodeId, subjNodeId);
                    toDelete.add(subjNodeId);
                }
            }
            removeChildrenById(nodeId, toDelete);
            addChildreById(nodeId, toAdd);
        }
        if (getNodeById(nodeId).getWord().toLowerCase().equals("when")) {
            setNodeWordById(nodeId, "time");
            setNodeTypeById(nodeId, POS_NOUN);
        }
        if (getNodeById(nodeId).getWord().toLowerCase().equals("where")) {

            setNodeWordById(nodeId, "location");
            setNodeTypeById(nodeId, POS_NOUN);
        }
        if (getNodeById(nodeId).getType().equals(POS_NOUN) || getNodeById(nodeId).getType().equals(POS_PROPERNOUN)) { // Nouns
            return;
        }
        if (getNodeById(nodeId).getType().startsWith(POS_PRONOUN)) {
            if (getNodeById(nodeId).getWord().toLowerCase().equals("you")) {
                if (getNodeById(nodeId).getRelation().equals(DEP_NOUN_SUBJECT)) {
                    return;
                } else {
                    getNodeById(nodeId).setFlag(Flag.DELETE);
                    return;
                }
            }
            if (!getNodeById(nodeId).getWord().toLowerCase().equals("me")) {
                return;
            }
        }
        setNodeFlagById(nodeId, Flag.DELETE);
    }

    public void resolveObjectRelation(int nodeId) {
        if (getNodeById(nodeId).getChildrenIds() != null) {
            ArrayList<Integer> toDelete = new ArrayList<>();
            int subjNodeId = NOT_EXIST;
            int dobjNodeId = NOT_EXIST;
            int iobjNodeId = NOT_EXIST;
            for (int childId : getNodeById(nodeId).getChildrenIds()) {
                if (getNodeById(childId).getRelation().equals(DEP_NOUN_SUBJECT))
                    subjNodeId = childId;
                if (getNodeById(childId).getRelation().equals(DEP_OBJECTIVE))
                    dobjNodeId = childId;
                if (getNodeById(childId).getRelation().equals(DEP_INDIRECT_OBJECTIVE))
                    iobjNodeId = childId;
            }
            if (subjNodeId != NOT_EXIST) {
                if (dobjNodeId != NOT_EXIST) {
                    setNodeParentId(subjNodeId, dobjNodeId);
                    addChildById(dobjNodeId, subjNodeId);
                    toDelete.add(subjNodeId);
                } else if (iobjNodeId != NOT_EXIST) {
                    setNodeParentId(subjNodeId, iobjNodeId);
                    addChildById(iobjNodeId, subjNodeId);
                    toDelete.add(subjNodeId);
                }
            }
            removeChildrenById(nodeId, toDelete);
        }

    }

    private void setRootId(int newRootId) {
        mRootId = newRootId;
        mNodeList.get(newRootId).setParentId(NOT_EXIST);
    }

    /**
     * Reduce the tree to keep only essential data
     */
    public void reduce(Trigger matchedTrigger) {
        preReduce(matchedTrigger);
        doReduce();
    }

    /**
     * Mark all nodes untagged or without trigger tags as to be deleted
     */
    private void preReduce(Trigger matchedTrigger) {
        for (int i = mNodeList.size() - 1; i > -1; i--) {
            Node node = mNodeList.valueAt(i);
            Set<Object> tagList = node.getTagList();
            tagList.retainAll(matchedTrigger.getAllTags());
            if (tagList.size() == 0)
                node.setFlag(Flag.DELETE);
        }
    }

    /**
     * Reduce the tree to keep only essential data
     * This should not be called directly, but
     * instead called from the other reduce function.
     */
    private void doReduce() {
        Node root = mNodeList.get(mRootId);
        recursiveReduce(mRootId);            //root Node
        if (root.getFlag() == Flag.DELETE) {
            Iterator<Integer> it = root.getChildrenIds().iterator();
            if (it.hasNext()) {
                int firstId = it.next();
                if (root.getChildrenIds().size() > 1) {
                    ArrayList<Integer> toDemote = new ArrayList<>();
                    int nodeId;
                    while (it.hasNext()) {
                        nodeId = it.next();
                        setNodeParentId(nodeId, root.getId());
                        toDemote.add(nodeId);
                    }
                    addChildreById(firstId, toDemote);
                    removeChildrenById(root.getId(), toDemote);
                }
                if (getNodeById(firstId).getChildrenIds().size() > 0) {
                    ArrayList<Integer> toPromote = new ArrayList<>();
                    Iterator<Integer> cIt = getNodeById(firstId).getChildrenIds().iterator();
                    int cFirstId = cIt.next(), cNodeId;
                    while (cIt.hasNext()) {
                        cNodeId = cIt.next();
                        if (getNodeById(cNodeId).getRelation().equals(DEP_CONJUNCTION) || getNodeById(cNodeId).getRelation().equals(DEP_COORDINATING_CONJUNCTION)) {
                            setNodeParentId(cNodeId, root.getId());
                            toPromote.add(cNodeId);
                        }
                    }
                    addChildreById(root.getId(), toPromote);
                    removeChildrenById(cFirstId, toPromote);
                }

                changeRoot(root.getChildrenIds().iterator().next());
                mNodeList.remove(root.getId());
            }
        }
        // advmod
        if (root.getChildrenIds().size() > 0) {
            int advmodNodeId = NOT_EXIST;
            for (Integer nodeId : root.getChildrenIds()) {
                if (getNodeById(nodeId).getRelation().equals(DEP_ADVERB_MODIFIER)) {
                    advmodNodeId = nodeId;
                    break;
                }
            }
            if (advmodNodeId != NOT_EXIST) {
                // swap the contents
                String tmp = getNodeById(advmodNodeId).getType();
                setNodeTypeById(advmodNodeId, root.getType());
                root.setType(tmp);
                tmp = getNodeById(advmodNodeId).getWord();
                setNodeWordById(advmodNodeId, root.getWord());
                root.setWord(tmp);
                tmp = getNodeById(advmodNodeId).getRelation();
                setRelationById(advmodNodeId, root.getRelation());
                root.setRelation(tmp);
                resolveObjectRelation(root.getId());
            }
        }

        removeFlagDelete();

        // TODO: check for a case which *doesn't* use advmod (when or where) but still requires some special handling re. subject and object?
    }


    private void removeFlagDelete() {
        ArrayList<Integer> deleteFlagNodes = new ArrayList<>();
        for (int i = mNodeList.size() - 1; i > -1; i--) {
            if (mNodeList.valueAt(i).getFlag() == Flag.DELETE) {
                mNodeList.removeAt(i);
            }
        }
    }

    /**
     * Change the root of the tree
     *
     * @author adamyi
     */
    public void changeRoot(int nodeId) {
        if (this.mRootId == nodeId)
            return;
        setRootId(nodeId);
        Node currentNode = mNodeList.get(nodeId);
        Stack<Node> ancestors = new Stack<>();
        ancestors.push(currentNode);
        while (currentNode.mParentId != NOT_EXIST) {
            currentNode = mNodeList.get(currentNode.mParentId);
            ancestors.push(currentNode);
        }
        Node nextNode = ancestors.pop();
        while (!ancestors.isEmpty()) {
            currentNode = nextNode;
            if (ancestors.isEmpty()) {
                currentNode.mParentId = NOT_EXIST;
            } else {
                nextNode = ancestors.pop();
                currentNode.mParentId = nextNode.getId();
                currentNode.mChildrenIds.remove(nextNode.getId());
            }
        }
    }


    /**
     * @param tagList : A list of tags which are candidates may add to a node
     *                Add Tag to Every Tree Node
     */
    public void addTag(LongSparseArray<Tag> tagList) {
        for (int i = mNodeList.size() - 1; i > -1; i--) {
            Node node = mNodeList.valueAt(i);
            for (int j = tagList.size() - 1; j > -1; j--) {
                Tag tag = tagList.valueAt(j);
                if (tag.matchWord(node.getWord(), node.getEntity()))
                    node.addTag(tagList.keyAt(j));
            }
        }
    }

    /**
     * Override Function toString()
     * Use the Recursive Function toString() Defined in Node
     * To Print the ParseTree
     */
    @Override
    public String toString() {
        return print("", getRoot()).toString();
    }

    private StringBuilder print(String indent, Node node) {
        StringBuilder ret = new StringBuilder(indent);
        if (node.getRelation() != null)
            ret.append(node.getRelation());
        ret.append("(").append(node.getType()).append(" ").append(node.getWord()).append(")");
        if (node.getChildrenIds().size() > 0) {
            ret.append(" {\n");
            for (int childId : node.getChildrenIds()) {
                ret.append(print(indent + "  ", getNodeById(childId)));
            }
            ret.append(indent).append("}");
        }
        ret.append("\n");
        return ret;
    }

    public static ArrayList<ParseTree> split(ParseTree tree) {
        /*
        TODO: change this
        ArrayList<ParseTree> list = new ArrayList<>();
        if (tree.getRoot().getFlag() == FLAG_NORMAL) {
            list.add(new ParseTree(tree.getRoot()));
        } else {
            for (Node node : tree.getRoot().getChildren()) {
                list.add(new ParseTree(node));
            }
        }
        return list;*/
        return null;
    }

    public void setNodeList(SparseArray nodeList) {
        mNodeList = nodeList;
    }

    public SparseArray<Node> getNodeList() {
        return mNodeList;
    }


}