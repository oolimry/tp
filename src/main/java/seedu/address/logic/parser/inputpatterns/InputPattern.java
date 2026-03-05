package seedu.address.logic.parser.inputpatterns;

import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * A series of tokens that represent a valid command structure
 */
public class InputPattern extends ArrayList<Token> {
    final String label;

    public InputPattern(String _label, Token... tokens) {
        addAll(List.of(tokens));
        this.label = _label;
    }

    public Token getTokenWithId(String id) {
        for (Token token : this) {
            if (token.getId().equals(id)) {
                return token;
            }
        }
        return null;
    }

    public void assignSegmentsFromArgs(String args) throws ParseException {
        ArrayList<String> combinedSegments = getCombinedSegments(args);

        if (combinedSegments.size() < this.size()) {
            throw new ParseException("Too few fields inputted (TODO MORE DESCRIPTIVE)");
        } else if (combinedSegments.size() > this.size()) {
            throw new ParseException("Too many fields inputted (TODO MORE DESCRIPTIVE)");
        }

        for (int i = 0; i < combinedSegments.size(); i++) {
            String segment = combinedSegments.get(i);
            Token token = this.get(i);

            if (!token.matches(segment)) {
                throw new ParseException(segment + " does not match " + token.getPreview());
            }

            token.setAssignedSegment(segment);
        }
    }

    /**
     * Splits the input text into space seperated segments
     * Based on the input pattern, detect where to join segments together
     *
     * @param input the string as input
     * @return the list of segments that matches the token pattern
     */
    private ArrayList<String> getCombinedSegments(String input) {
        ArrayList<String> rawSegments = new ArrayList<String>();
        rawSegments.addAll(List.of(input.split(" ")));
        if (!input.isEmpty() && input.charAt(input.length() - 1) == ' ') {
            rawSegments.add("");
        }

        ArrayList<String> combinedSegments = new ArrayList<>();

        int segmentPointer = 0;
        for (int i = 0; i < this.size(); i++) {
            if (segmentPointer == rawSegments.size()) {
                break;
            }

            Token token = this.get(i);
            if (token.allowSpaces()) {
                ArrayList<String> segmentsToJoin = new ArrayList<>();

                if (i != this.size() - 1) {
                    // case 1: there is a next token
                    // find the position and add everything in between
                    Token nextToken = this.get(i+1);

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        if (nextToken.matches(nextSegment)) {
                            break;
                        }

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                } else {
                    // case 2: there is no next token
                    // just keep appending all the way to the end

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                }

                String segmentToAdd = String.join(" ", segmentsToJoin);
                combinedSegments.add(segmentToAdd);
            } else {
                combinedSegments.add(rawSegments.get(segmentPointer));
                segmentPointer += 1;
            }
        }

        while (segmentPointer < rawSegments.size()) {
            combinedSegments.add(rawSegments.get(segmentPointer));
            segmentPointer += 1;
        }

        return combinedSegments;
    }
}
