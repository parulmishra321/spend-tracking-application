package com.flex83.app.services;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDBService {
    void create(String collectionName, Document data);

    void create(String collectionName, List<Document> data);

    void createCollection(String collectionName);

    void createIndex(String collection, Document index, Boolean unique);

    void setTTLForCollection(String collection, String field, Long expirationTime);

    public Document findOne(String collection, Document query, Document projection, Document sort);

    Document findOne(String collectionName, Document query, Document projection);
    Document findById(String collection, String id, Document projection);

    Document findOne(String collection, Document query, Document projection, String sortBy, Integer sortType);

    void update(String collection, Document filter, Document set, Document unset, Document push, Document pushToSet, boolean multi);

    void update(String collection, Document filter, Document set);

    List<Document> findList(String collection, Document matchEq, Document projection, String sortBy, Integer sortType);

    List<Document> findList(String collection, Document matchEq, Document projection, Document sort, Integer offset, Integer max);

    List<Document> findList(String collection, Document matchEq, Document projection);

    void dropCollection(String collectionName);

    void clearCollection(String collectionName, Document filter);

    List<String> getAllCollections();

    void deleteOne(String collectionName, Document query);
    void deleteMany(String collectionName, Document query);
}
