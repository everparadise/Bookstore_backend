package com.example.main.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data

@Node
public class Tag {
    @Id
    private String name;

    @Relationship(type = "RELATES_TO")
    public Set<Tag> relatedTags;

    public Tag(String name){
        this.name = name;
        relatedTags = new HashSet<>();
    }
    public void addRelatedTag(Tag tag){
        if(this.relatedTags == null)
            relatedTags = new HashSet<>();
        this.relatedTags.add(tag);
    }
}
