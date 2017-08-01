package edu.cmu.chimps.messageontap_api;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import edu.cmu.chimps.messageontap_api.Tag;

public class ParseTree {

    public static final int NOT_EXIST = -1;

    /**
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

    /**
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

    public enum Mood {UNKNOWN, IMPERATIVE, INTERROGTIVE}

    public enum Direction {UNKNOWN, INCOMING, OUTGOING}

    public enum Flag {NORMAL, DELETE, MERGE}

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
        private Set<String> mTagSet;

        public Node() {
            this.mChildrenIds = new HashSet<>();
            this.mTagSet = new HashSet<>();
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

        public Set<String> getTagList() {
            return mTagSet;
        }

        public void setTagList(Set<String> tagSet) {
            this.mTagSet = tagSet;
        }

        public void addTag(String t) {
            mTagSet.add(t);
        }

        public void addTag(Tag t) {
            mTagSet.add(t.getName());
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

    public Node getRoot() {
        return mNodeList.get(mRootId);
    }

    public boolean isConcatenation(Node a, Node b) {
        return a.getParentId() == b.getParentId();
    }

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

    //Need to change to Mood
    public void moodDetection() {
        for (int i = 0; i < mNodeList.size(); i++) {
            if (mNodeList.get(i).getType().equals(DEP_PUNCTUATION)) {
                if (mNodeList.get(i).getWord().equals("?")) {
                    mood = Mood.INTERROGTIVE;
                }
            }
            if (getRoot().getType().equals(POS_VERB)) {
                for (int childId : getRoot().getChildrenIds()) {
                    if (getNodeById(childId).getType().equals(DEP_NOUN_SUBJECT)) {
                        mood = Mood.INTERROGTIVE;
                    }
                }
                mood = Mood.IMPERATIVE;
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

    public void reduce(int nodeId) {
        if (getNodeById(nodeId).getChildrenIds() != null) {
            ArrayList<Integer> toDelete = new ArrayList<>();
            ArrayList<Integer> toAdd = new ArrayList<>();
            int subjNodeId = NOT_EXIST;
            int dobjNodeId = NOT_EXIST;
            int iobjNodeId = NOT_EXIST;
            for (int childId : getNodeById(nodeId).getChildrenIds()) {
                reduce(childId);
                switch (getNodeById(nodeId).getFlag()) {
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
                        break;
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

    public boolean changeRoot(int newRootId) {
        mRootId = newRootId;
        mNodeList.get(mRootId).setParentId(NOT_EXIST);
        return true;
    }

    public boolean setRootId(int newRootId) {
        mRootId = newRootId;
        return true;
    }

    public void reduce() {
        Node root = mNodeList.get(mRootId);
        reduce(root.getId());            //root Node
        if (root.getFlag() == Flag.DELETE) {
            Iterator<Integer> it = root.getChildrenIds().iterator();
            int firstId = it.next();
            if (root.getChildrenIds().size() > 1) {
                ArrayList<Integer> toDemote = new ArrayList<>();
                int nodeId = 0;
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
                int cFirstId = cIt.next(), cNodeId = 0;
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
            if (root.getChildrenIds().size() == 1) {
                changeRoot(root.getChildrenIds().iterator().next());
            }
        }
        // advmod
        if (root.getChildrenIds().size() > 0) {
            int advmodNodeId = NOT_EXIST;
            int nodeId = 0;
            Iterator<Integer> it = root.getChildrenIds().iterator();
            while (it.hasNext()) {
                nodeId = it.next();
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

        // TODO: check for a case which *doesn't* use advmod (when or where) but still requires some special handling re. subject and object?
    }

    /**
     * @param context : context of Service (SemanticUnderstanding)
     * @param tagList : A list of tags which are candidates may add to a node
     *                Add Tag to Every Tree Node
     */
    public void addTag(ArrayList<Tag> tagList, Context context) {
        addTag(getRoot(), tagList, context);
    }

    /**
     * @param node    : Start from Root Node
     * @param tagList : A list of tags which are candidates may add to a node
     * @param context : context of Service (Used in AsyncTask of getting results Concept Graph)
     *                Recursive Function （Post-order traversal）
     *                Look Through All the Tree Nodes to Add Tags (By Concept Graph)
     */
    public void addTag(Node node, ArrayList<Tag> tagList, Context context) {
        for (Tag t : tagList) {
            if (t.matchWord(node.getWord(), node.getEntity()))
                node.addTag(t);
        }
    }

    /**
     * Override Function toString()
     * Use the Recursive Function toString() Defined in Node
     * To Print the ParseTree
     */
    @Override
    public String toString() {
        return print("", getRoot());
    }

    private String print(String indent, Node node) {
        String ret = indent;
        if (node.getRelation() != null) ret += node.getRelation();
        ret += "(" + node.getType() + " " + node.getWord() + ")";
        if (node.getChildrenIds().size() > 0) {
            ret += " {\n";
            for (int childId : node.getChildrenIds()) {
                ret += print(indent + "  ", getNodeById(childId));
            }
            ret += indent + "}";
        }
        ret += "\n";
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