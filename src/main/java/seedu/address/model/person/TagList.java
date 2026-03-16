package seedu.address.model.person;

import java.util.List;
import java.util.TreeMap;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person's list of tag name-value pairs.
 */
public class TagList {
    public TreeMap<String, String> tags;

    public TagList() {
        this.tags = new TreeMap<>();
    }

    public TagList(TagList other) {
        this.tags = new TreeMap<>();
        this.tags.putAll(other.tags);
    }

    public TagList(String... tagStrings) {
        this.tags = new TreeMap<>();
        for (String tagString : tagStrings) {
            this.tags.put(Tag.getNameFromRaw(tagString), Tag.getValueFromRaw(tagString));
        }
    }

    public TagList(List<Tag> tags) {
        this.tags = new TreeMap<>();
        for (Tag tag : tags) {
            this.addTag(tag);
        }
    }

    public void addTag(Tag tag) {
        this.tags.put(tag.tagName, tag.tagValue);
    }

    public void editTag(Tag tag) {
        this.tags.replace(tag.tagName, tag.tagValue);
    }

    public void deleteTag(Tag tag) {
        this.tags.remove(tag.tagName);
    }

    public boolean containsTagName(String tagName) {
        return this.tags.containsKey(tagName);
    }

    public List<String> viewTags() {
        return this.tags.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue()).toList();
    }

    @Override
    public String toString() {
        return this.viewTags().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagList)) {
            return false;
        }

        return this.toString().equals(other.toString());
    }
}
