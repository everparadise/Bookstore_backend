package com.example.main.repository;

import com.example.main.model.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends Neo4jRepository<Tag, String> {
    @Query(value="MATCH (t:Tag)-[:RELATES_TO*1..2]-(t2:Tag) WHERE t.name = $name RETURN DISTINCT t2.name")
    List<String> findRelatedTags(String name);
}
