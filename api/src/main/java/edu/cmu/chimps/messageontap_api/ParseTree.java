package edu.cmu.chimps.messageontap_api;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import edu.cmu.chimps.messageontap_api.Tag;

public class ParseTree {

    /**
     * Constants for Word Flag
     */
    public static final int NOT_EXIST = -1;
    private static final int FLAG_NORMAL = 0;
    private static final int FLAG_DELETE = 1;
    private static final int FLAG_MERGE = 2;

    /**
     * Constants for ParseTree Mood
     */
    private static final int MOOD_IMPERATIVE = 1;
    private static final int MOOD_INTERROGTIVE = 2;

    /**
     * Universal POS tags by Stanford
     */
    private static final String POS_ADJECTIVE = "ADJ";
    private static final String POS_ADVERB = "ADV";
    private static final String POS_VERB = "VERB";

    private static final String POS_NUMERAL = "NUM";
    private static final String POS_NOUN = "NOUN";
    private static final String POS_PROPERNOUN = "PROPN";
    private static final String POS_PRONOUN = "PRON";

    private static final String POS_COORDINATING_CONJUNCTION = "CCONJ";
    private static final String POS_SUBORDINATING_CONJUNCTION = "SCONJ";

    private static final String POS_ADPOSITION = "ADP";
    private static final String POS_AUXILIARY = "AUX";
    private static final String POS_DETERMINER = "DET";
    private static final String POS_INTERJECTION = "INTJ";

    private static final String POS_PARTICLE = "PART";
    private static final String POS_PUNCTUAATION = "PUNCT";
    private static final String POS_SYMBOL = "SYM";

    private static final String POS_UNKNOWN = "X";

    /**
     * Universal Dependencies
     */
    private static final String DEP_NOUN_SUBJECT = "NSUBJ";
    private static final String DEP_OBJECTIVE = "OBJ";
    private static final String DEP_INDIRECT_OBJECTIVE = "IOBJ"; // Tom teaches Sam (direct obj) math (indirect obj)

    private static final String DEP_CLAUSE_SUBJECT = "CSUBJ";
    private static final String DEP_CLAUSAL_COMPLEMENT = "CCOMP"; // this one has its own subject. eg. Adam says that Mars likes to swim.
    private static final String DEP_OPEN_CLAUSAL_COMPLEMENT = "XCOMP"; // this one does not. eg Fanglin looks great.

    private static final String DEP_OBLIQUE_NOMINAL = "OBL";
    private static final String DEP_VOCATIVE = "VOCATIVE";
    private static final String DEP_EXPLETIVE = "EXPL";
    private static final String DEP_DISLOCATED = "DISLOCATED";

    private static final String DEP_AUXILIARY = "AUX";
    private static final String DEP_COPULA = "COP";
    private static final String DEP_MARKER = "MARK";

    private static final String DEP_ADVERB_CLAUSE_MODIFIER = "ADVCL";
    private static final String DEP_ADVERB_MODIFIER = "ADVMOB";
    private static final String DEP_NOMINAL_MODIFIER = "NMOD";
    private static final String DEP_APPOSITIONAL_MODIFIER = "APPOS";
    private static final String DEP_NUMERIC_MODIFIER = "NUMMOD";
    private static final String DEP_CLAUSAL_MODIFIER = "ACL";
    private static final String DEP_ADJECTIVE_MODIFIER = "AMOD";

    private static final String DEP_DISCOURSE = "DISCOURSE";
    private static final String DEP_DETERMINER = "DET";
    private static final String DEP_CLASSIFIER = "CLF";
    private static final String DEP_CASE = "CASE";

    private static final String DEP_CONJUNCTION = "CONJ";
    private static final String DEP_COORDINATING_CONJUNCTION = "CC";

    private static final String DEP_FIXED_MULTIWORD_EXPRESSION = "FIXED";
    private static final String DEP_FLAT_MULTIWORDD_EXPRESSION = "FLAT";
    private static final String DEP_COMPOUND = "COMPOUND";

    private static final String DEP_LIST = "LIST";
    private static final String DEP_PARATAXIS = "PARATAXIS";

    private static final String DEP_ORPHAN = "ORPHAN";
    private static final String DEP_GOES_WITH = "GOESWITH";
    private static final String DEP_REPARANDUM = "REPARANDUM"; //overridden disfluency

    private static final String DEP_PUNCTUATION = "PUNCT";
    private static final String DEP_ROOT = "ROOT";
    private static final String DEP_UNKNOWN = "DEP";

    enum Mood {MOOD_UNKNOWN, MOOD_IMPERATIVE, MOOD_INTERROGTIVE}

    enum Direction {UNKNOWN_DIRECTION, INCOMING_DIRECTION, OUTGOING_DIRECTION}

    /**
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
    public class Node {

        private int mId;
        private String mType;
        private String mWord;
        private String mEntity;
        private Set<Integer> mChildrenIds;
        private int mParentId;
        private String mRelation;
        private int mFlag; // 0 = normal, 1 = delete, 2 = merge
        private ArrayList<Tag> mTagList;

        public Node() {
            this.mChildrenIds = new HashSet<>();
            this.mFlag = FLAG_DELETE;
        }

        public void setValue(String type, String word) {
            this.mType = type;
            this.mWord = word;
            //this.entity = null;
            //this.parentId = null;
            //this.childrenIds = new ArrayList<>();
            this.mFlag = FLAG_NORMAL;
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

        public int getFlag() {
            return mFlag;
        }

        public void setFlag(int flag) {
            this.mFlag = flag;
        }

        public ArrayList<Tag> getTagList() {
            return mTagList;
        }

        public void setTagList(ArrayList<Tag> tagList) {
            this.mTagList = tagList;
        }

        public void addTag(Tag t) {
            ArrayList<Tag> tagList = this.getTagList();
            tagList.add(t);
            this.setTagList(tagList);
        }

        @Override
        public String toString() {
            return print("");
        }

        private String print(String indent) {
            String ret = indent;
            if (this.getRelation() != null) ret += this.getRelation();
            ret += "(" + getType() + " " + getWord() + ")";
            if (getChildrenIds().size() > 0) {
                ret += " {\n";
                for (int childId : getChildrenIds()) {
                    ret += getNodeById(childId).print(indent + "  ");
                }
                ret += indent + "}";
            }
            ret += "\n";
            return ret;
        }

    }

    SparseArray<Node> mNodeList;
    int mRootId;
    public long[] time;

    public Mood mood;
    public Direction direction;

    public ParseTree() {
        this.mNodeList = new SparseArray<>();
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

    private void setNodeFlagById(int nodeId, int flag) {
        mNodeList.get(nodeId).setFlag(flag);
    }

    private void setRelationById(int advmodNodeId, String relation) {
        mNodeList.get(advmodNodeId).setRelation(relation);
    }

    public Node getRoot() {
        return mNodeList.get(mRootId);
    }

    public Node getNodeById(int id) {
        return mNodeList.get(id);
    }

    private boolean changeRoot(int newmRootId) {
        mRootId = newmRootId;
        return true;
    }


    /**
     * Override Function toString()
     * Use the Recursive Function toString() Defined in Node
     * To Print the ParseTree
     */
    @Override
    public String toString() {
        return getRoot().toString();
    }


}