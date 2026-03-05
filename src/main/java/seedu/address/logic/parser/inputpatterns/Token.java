package seedu.address.logic.parser.inputpatterns;

/**
 * An abstract class for representing a single token
 * A command consists of several tokens following an InputPattern
 */
public abstract class Token {

    private String id;
    private String assignedSegment;

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAssignedSegment() {
        return assignedSegment;
    }

    public void setAssignedSegment(String assignedSegment) {
        this.assignedSegment = assignedSegment;
    }

    /**
     * @return the string that should appear as a suggestion
     */
    abstract public String getPreview();

    /**
     * @param segment a string entered for a token
     * @return whether the segment is a valid string matching the token's requirements
     */
    abstract public boolean matches(String segment);


    /**
     * @param segment a string entered for a token
     * @return if the segment is definitely not a prefix of a valid input of the token, returns false.
     *         else it returns true
     */
    public boolean prefixMatches(String segment) {
        return true;
    }

    /**
     * @return whether this token allow spaces, which by default is false
     */
    public boolean allowSpaces() {
        return false;
    }



}
