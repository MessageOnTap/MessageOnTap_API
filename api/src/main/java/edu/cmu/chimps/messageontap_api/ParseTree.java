package edu.cmu.chimps.messageontap_api;

import android.util.SparseArray;

import java.util.ArrayList;

public class ParseTree {

    enum Flag{NORMAL, DELETE, MERGE}

    public class Node {

        private int mId;
        private String mType;
        private String mWord;
        private String mEntity;
        private ArrayList<Integer> mChildrenIds;
        private int mParentId;
        private String mRelation;
        private Flag mFlag;
        private ArrayList<Tag> mTagList;

        public Node() {
            this.mChildrenIds = new ArrayList<>();
            this.mFlag = Flag.DELETE;
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

        public ArrayList<Integer> getChildrenIds() {
            return mChildrenIds;
        }

        public void addChildrenId(int id) {
            mChildrenIds.add(id);
        }

        public void setChildrenIds(ArrayList<Integer> childrenIds) {
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
            return this.print("");
        }

        private String print(String indent) {
            /*
            TODO: write this
            String ret = indent;
            if (this.getRelation() != null) ret += this.getRelation();
            ret += "(" + getType() + " " + getWord() + ")";
            ret += "\n";
            indent += "  ";
            if (getChildrenIds().size() > 0) {
                for (Node child : getChildrenIds()) {
                    ret += child.print(indent);
                }
            }
            return ret;*/
            return "";
        }

    }

    public SparseArray<Node> mNodeList;
    int mRootId;
    public Trigger.Mood mood;
    public Trigger.Direction direction;
}
