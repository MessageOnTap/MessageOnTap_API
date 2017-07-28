package edu.cmu.chimps.messageontap_api;


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


    enum Mood{MOOD_UNKNOWN, MOOD_IMPERATIVE, MOOD_INTERROGTIVE}
    enum Relation{UNKNOWN, CONCATENATION, SUBORDINATE}
    enum Direction {UNKNOWN_DIRECTION, INCOMING_DIRECTION, OUTGOING_DIRECTION}

    private class Constraint{
        //TODO
        String tagA_name = "";
        String tagB_name = "";
        Relation relation = Relation.UNKNOWN;
    }

    private Set<String> mMadatoryTags = new HashSet<>();

    private Set<String> mOptionalTags = new HashSet<>();

    private Set<Constraint> mConstraints = new HashSet<>();

    private Mood mMood = Mood.MOOD_UNKNOWN;

    private Direction mDirection = Direction.UNKNOWN_DIRECTION;

    private String mPackageName; //TODO: Plugin Id

    public Trigger() {
        mMadatoryTags = new HashSet<String>();
        mOptionalTags = new HashSet<String>();
        mConstraints = new HashSet<Constraint>();
        mMood = Mood.MOOD_UNKNOWN;
        mDirection = Direction.UNKNOWN_DIRECTION;
    }

    public Trigger(Set<Tag> pluginMadatoryTagSet) {
        for(Tag t:pluginMadatoryTagSet) {
            mMadatoryTags.addAll(t.getKeywordList());
        }
    }

    public Trigger(Set<Tag> pluginMadatoryTagSet, Set<Tag> pluginOptionalTagSet) {
        for(Tag mandatoryTag:pluginMadatoryTagSet) {
            mMadatoryTags.addAll(mandatoryTag.getKeywordList());
        }
        for(Tag optionalTag:pluginOptionalTagSet) {
            mMadatoryTags.addAll(optionalTag.getKeywordList());
        }
    }

    /**
     * Check Only Madatory Tags
     * @param tagSet
     * @return
     */
    public boolean activateTrigger(Set<String> tagSet) {
        for (String mKey : tagSet) {
            if (!mMadatoryTags.contains(mKey)) {
                return false;
            }
        }
        return true;
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
}
