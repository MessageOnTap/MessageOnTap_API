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

    enum Relation {UNKNOWN, CONCATENATION, DIRECT_SUBORDINATION, NESTED_SUBORNIDATION}

    public class Constraint {
        //TODO
        String tagA_name = "";
        String tagB_name = "";
        Relation relation = Relation.UNKNOWN;
    }

    private String mName = "null";

    private Set<String> mMadatoryTags = new HashSet<>();

    private Set<String> mOptionalTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private ParseTree.Mood mMood = ParseTree.Mood.UNKNOWN;

    private ParseTree.Direction mDirection = ParseTree.Direction.UNKNOWN;

    private String mPackageName; //TODO: Plugin Id

    public Trigger() {
        mName = "";
        mMadatoryTags = new HashSet<String>();
        mOptionalTags = new HashSet<String>();
        mConstraints = new HashSet<Constraint>();
        mMood = ParseTree.Mood.UNKNOWN;
        mDirection = ParseTree.Direction.UNKNOWN;
    }

    public Trigger(String name, Set<String> pluginMadatoryTags) {
        this.mName = name;
        for (String t : pluginMadatoryTags) {
            mMadatoryTags.add(t);
        }
    }

    public Trigger(String name, Set<String> pluginMadatoryTags, Set<String> pluginOptionalTags) {
        this.mName = name;
        for (String t : pluginMadatoryTags) {
            mMadatoryTags.add(t);

        }
        for (String t : pluginOptionalTags) {
            mOptionalTags.add(t);

        }
    }

    public Trigger(String name, Set<String> pluginMadatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints) {
        this.mName = name;
        for (String t : pluginMadatoryTags) {
            mMadatoryTags.add(t);

        }
        for (String t : pluginOptionalTags) {
            mOptionalTags.add(t);

        }
        this.mConstraints = constraints;
    }

    public Trigger(String name, Set<String> pluginMadatoryTags, Set<String> pluginOptionalTags, Set<Constraint> constraints
            , ParseTree.Mood mood, ParseTree.Direction direction) {
        this.mName = name;
        for (String t : pluginMadatoryTags) {
            mMadatoryTags.add(t);

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
        HashMap<String, Integer> tagNames = new HashMap<>();
        for (int i = 0; i < parseTree.mNodeList.size(); i++) {
            if (!parseTree.getNodeById(i).getTagList().isEmpty()) {
                for (String t : parseTree.mNodeList.get(i).getTagList()) {
                    tagNames.put(t, i);
                }
            }
        }

        if (!mMadatoryTags.isEmpty()) {
            if (!tagNames.keySet().contains(mMadatoryTags)) {
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

}
