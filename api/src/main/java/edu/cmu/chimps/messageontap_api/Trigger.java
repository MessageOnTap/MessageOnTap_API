package edu.cmu.chimps.messageontap_api;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the data type for plugins to set up their triggers at message perception
 * 1. Tag List
 *
 */

/**
 *     Trigger

 + Set<Tag> mandatoryTags

 + Set<Tag> optionalTags

 + Set<Constraint> contraints

 + int mood

 + int direction

 */
public class Trigger {


    private static final int ROOT_PARENT_ID = -1;

    public boolean isConcatenation(ParseTree parseTree, ParseTree.Node a, ParseTree.Node b){
        //TODO : ROOT NODE
        if(a.getParentId() == ROOT_PARENT_ID){
            return false;
        }
        else{
            if(parseTree.mNodeList.get(a.getParentId()).getChildrenIds().contains(b.getId())){
                return true;
            }
        }
        return true;
    }

    public boolean isSubordinate(ParseTree.Node a, ParseTree.Node b){
        //TODO : ROOT NODE
        if(a.getChildrenIds() == null){
            return false;
        }
        else{
            if(a.getChildrenIds().contains(b.getId())){
                return true;
            }
        }
        return true;
    }

    enum Relation{UNKNOWN, CONCATENATION, SUBORDINATE}

    private class Constraint{
        //TODO
        String tagA_name = "";
        String tagB_name = "";
        Relation relation = Relation.UNKNOWN;
    }

    private String name = "null";

    private Set<String> mMadatoryTags = new HashSet<>();

    private Set<String> mOptionalTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private ParseTree.Mood mMood = ParseTree.Mood.MOOD_UNKNOWN;

    private ParseTree.Direction mDirection = ParseTree.Direction.UNKNOWN_DIRECTION;

    private String mPackageName; //TODO: Plugin Id

    public Trigger() {
        name = "";
        mMadatoryTags = new HashSet<String>();
        mOptionalTags = new HashSet<String>();
        mConstraints = new HashSet<Constraint>();
        mMood = ParseTree.Mood.MOOD_UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN_DIRECTION;
    }

    public Trigger(String name, Set<Tag> pluginMadatoryTags) {
        this.name = name;
        for(Tag t:pluginMadatoryTags) {
            mMadatoryTags.add(t.getName());
        }
    }

    public Trigger(String name, Set<Tag> pluginMadatoryTags, Set<Tag> pluginOptionalTags) {
        this.name = name;
        for(Tag t:pluginMadatoryTags) {
            mMadatoryTags.add(t.getName());

        }
        for(Tag t:pluginOptionalTags) {
            mOptionalTags.add(t.getName());

        }
    }

    public Trigger(String name, Set<Tag> pluginMadatoryTags, Set<Tag> pluginOptionalTags, Set<Constraint> constraints){
        this.name = name;
        for(Tag t:pluginMadatoryTags) {
            mMadatoryTags.add(t.getName());

        }
        for(Tag t:pluginOptionalTags) {
            mOptionalTags.add(t.getName());

        }
        this.mConstraints = constraints;
    }

    public Trigger(String name, Set<Tag> pluginMadatoryTags, Set<Tag> pluginOptionalTags, Set<Constraint> constraints
                    , ParseTree.Mood mood, ParseTree.Direction direction){
        this.name = name;
        for(Tag t:pluginMadatoryTags) {
            mMadatoryTags.add(t.getName());

        }
        for(Tag t:pluginOptionalTags) {
            mOptionalTags.add(t.getName());

        }
        if(constraints != null)
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

    public String getJson() {
        return DataUtils.SimpleObjectToJson(this, Globals.TYPE_TRIGGER);
    }

    public String getTypeKey() {
        return Globals.TYPE_TRIGGER;
    }

    public boolean matchTrigger(ParseTree parseTree){
        // 1. Tag List
        // 2. Tag Relation
        // 3. Mood? Direction?

        boolean flag = false;
        //Direction Judge
        //Incoming or Outgoing?
        if(mDirection != ParseTree.Direction.UNKNOWN_DIRECTION) {
            if (parseTree.direction != mDirection) {
                return false;
            }
        }

        //Mood Judge
        //IMPERATIVE, INTERROGTIVE
        if(mMood != ParseTree.Mood.MOOD_UNKNOWN) {
            if (parseTree.mood != mMood) {
                return false;
            }
        }

        //Tag Judge Contains

        //Add Tags from ParseTree
        HashMap<String, Integer> tagNames = new HashMap<>();
        for (int i = 0; i < parseTree.mNodeList.size(); i++) {
            if (!parseTree.mNodeList.get(i).getTagList().isEmpty()) {
                for (Tag t : parseTree.mNodeList.get(i).getTagList()) {
                    tagNames.put(t.getName(), i);
                }
            }
        }

        if(!mMadatoryTags.isEmpty()) {
            if(!tagNames.keySet().contains(mMadatoryTags)){
                return false;
            }
        }

        //A -> B
        if(!mConstraints.isEmpty()){
            for(Constraint c:mConstraints){
                String tagA = c.tagA_name;
                String tagB = c.tagB_name;
                Relation relation = c.relation;
                ParseTree.Node nodeA = parseTree.mNodeList.get(tagNames.get(tagA));
                ParseTree.Node nodeB = parseTree.mNodeList.get(tagNames.get(tagB));
                if(relation == Relation.CONCATENATION){
                    if(!isConcatenation(parseTree, nodeA, nodeB)){
                        return false;
                    }
                }
                if(relation == Relation.SUBORDINATE){
                    if(!isSubordinate(nodeA, nodeB)){
                        return false;
                    }
                }

            }
        }

        return true;
    }

}
