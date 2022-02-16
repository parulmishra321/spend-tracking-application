package com.spendTracker.app.services;

import com.spendTracker.app.context.RequestContext;
import com.spendTracker.app.entities.TenantDatabaseConfig;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MongoDBClientServices {
    private static final Logger LOG = LogManager.getLogger(MongoDBClientServices.class);
    private static ConcurrentHashMap<String, MongoClient> mongoClients = new ConcurrentHashMap<>();

    private MongoClient getMongoClient(TenantDatabaseConfig tenantDatabaseConfig) {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder().writeConcern(WriteConcern.JOURNALED).codecRegistry(pojoCodecRegistry);

        MongoCredential mongoCredential = MongoCredential.createCredential(tenantDatabaseConfig.getUsername(), tenantDatabaseConfig.getDatabaseName(), tenantDatabaseConfig.getPassword().toCharArray());
        List<ServerAddress> serverAddressList = new ArrayList<>();
        serverAddressList.add(new ServerAddress(tenantDatabaseConfig.getServer(), Integer.valueOf(tenantDatabaseConfig.getPort())));

        return new MongoClient(serverAddressList, mongoCredential, mongoClientBuilder.build());
    }

    public MongoDatabase getDB() {
        TenantDatabaseConfig tenantDatabaseConfig = RequestContext._mongoConfiguration.get();
        MongoClient mongoClient = mongoClients.get(RequestContext._currentTenant.get());
        if (mongoClient == null) {
            mongoClient = getMongoClient(tenantDatabaseConfig);
            mongoClients.put(RequestContext._currentTenant.get(), mongoClient);
        }
        return mongoClient.getDatabase(tenantDatabaseConfig.getDatabaseName());
    }
}
