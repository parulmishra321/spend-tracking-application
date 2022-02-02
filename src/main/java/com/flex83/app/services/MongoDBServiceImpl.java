package com.flex83.app.services;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository("mongoDBRepositoryImpl")
public class MongoDBServiceImpl implements MongoDBService {
    @Autowired
    private MongoDBClientServices mongoDBClientServices;

    @Override
    public void create(String collectionName, Document data) {
        mongoDBClientServices.getDB().getCollection(collectionName).insertOne(data);
    }

    @Override
    public void create(String collectionName, List<Document> data) {

    }

    @Override
    public void createCollection(String collectionName) {
        mongoDBClientServices.getDB().createCollection(collectionName);
    }

    @Override
    public void createIndex(String collection, Document index, Boolean unique) {
        IndexOptions options = new IndexOptions();
        options.unique(unique);
        mongoDBClientServices.getDB().getCollection(collection).createIndex(index, options);
    }

    @Override
    public void setTTLForCollection(String collection, String field, Long expirationTime) {
        mongoDBClientServices.getDB().getCollection(collection).createIndex(new Document(field, 1), new IndexOptions().expireAfter(expirationTime, TimeUnit.SECONDS));
    }

    @Override
    public Document findOne(String collectionName, Document query, Document projection) {
        return findOne(collectionName, query, projection, null);
    }

    @Override
    public Document findOne(String collection, Document query, Document projection, String sortBy, Integer sortType) {
        return null;
    }

    @Override
    public Document findOne(String collection, Document query, Document projection, Document sort) {
        if (Objects.isNull(query)) {
            query = new Document();
        }
        FindIterable<Document> iterable = mongoDBClientServices.getDB().getCollection(collection).find(query);
        if (Objects.nonNull(projection)) {
            iterable = iterable.projection(projection);
        }
        if (Objects.nonNull(sort)) {
            iterable = iterable.sort(sort);
        }
        Document document = iterable.first();
        return Objects.nonNull(document) ? document : new Document();
    }


    @Override
    public void update(String collection, Document filter, Document set, Document unset, Document push, Document pushToSet, boolean multi) {
        update(collection, filter, set, unset, push, null, pushToSet, null, multi, true);
    }

    @Override
    public void update(String collection, Document filter, Document set) {
        update(collection, filter, set, null, null, null, null, null, false, true);
    }

    private UpdateResult update(String collection, Document filter, Document set, Document unset, Document push, Document pull, Document pushToSet, Document increment, boolean multi, boolean upsert) {
        long t1 = Calendar.getInstance().getTimeInMillis();

        Document update = new Document();
        if (Objects.nonNull(set)) {
            update.put("$set", set);
        }
        if (Objects.nonNull(pull)) {
            update.put("$pull", pull);
        }
        if (Objects.nonNull(unset)) {
            update.put("$unset", unset);
        }
        if (Objects.nonNull(push)) {
            update.put("$push", push);
        }
        if (Objects.nonNull(pushToSet) && !pushToSet.isEmpty()) {
            update.append("$addToSet", pushToSet);
        }
        if (Objects.nonNull(increment)) {
            update.put("$inc", increment);
        }
        return updateClauses(collection, filter, update, multi, upsert, t1);
    }

    private UpdateResult updateClauses(String collection, Document filter, Document update, boolean multi, boolean upsert, long t1) {
        UpdateResult updateResult = null;
        UpdateOptions updateOptions = new UpdateOptions().upsert(upsert);
        if (multi) {
            updateResult = mongoDBClientServices.getDB().getCollection(collection).updateMany(filter, update, updateOptions);
        } else {
            updateResult = mongoDBClientServices.getDB().getCollection(collection).updateOne(filter, update, updateOptions);
        }
        return updateResult;
    }

    @Override
    public List<Document> findList(String collection, Document matchEq, Document projection, String sortBy, Integer sortType) {
        Document query = new Document();
        if (Objects.nonNull(matchEq)) {
            matchEq.forEach(query::put);
        }
        Document sort = new Document();
        sort.put(sortBy, sortType);
        return findList(collection, query, projection, sort, null, null);
    }

    @Override
    public List<Document> findList(String collection, Document query, Document projection, Document sort, Integer offset, Integer max) {
        if (Objects.isNull(query)) {
            query = new Document();
        }
        FindIterable<Document> iterable = mongoDBClientServices.getDB().getCollection(collection).find(query);
        if (Objects.nonNull(projection)) {
            iterable = iterable.projection(projection);
        }
        if (Objects.nonNull(sort)) {
            iterable = iterable.sort(sort);
        }
        if (Objects.nonNull(offset)) {
            iterable = iterable.skip(offset);
        }
        if (Objects.nonNull(max)) {
            iterable = iterable.limit(max);
        }
        List<Document> documents = iterable.into(new ArrayList<Document>());
        return documents;
    }

    @Override
    public List<Document> findList(String collection, Document matchEq, Document projection) {
        return findList(collection, matchEq, projection, null, null, null);
    }

    @Override
    public void dropCollection(String collectionName) {
        mongoDBClientServices.getDB().getCollection(collectionName).drop();
    }

    @Override
    public void clearCollection(String collectionName, Document filter) {
        mongoDBClientServices.getDB().getCollection(collectionName).deleteMany(filter);
    }

    @Override
    public List<String> getAllCollections() {
        return mongoDBClientServices.getDB().listCollectionNames().into(new ArrayList<>());
    }

    @Override
    public void deleteOne(String collectionName, Document query) {
        mongoDBClientServices.getDB().getCollection(collectionName).deleteOne(query);
    }

    @Override
    public void deleteMany(String collectionName, Document query) {
        mongoDBClientServices.getDB().getCollection(collectionName).deleteMany(query);
    }
}
