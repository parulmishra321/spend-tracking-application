package com.flex83.app.bussiness;

import com.flex83.app.request.GroupCreateRequest;
import com.flex83.app.response.GroupDetails;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.CommonUtils;
import com.flex83.app.utils.GenerateUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.flex83.app.constant.MongoDBConstants.GROUPS_COLLECTION;
import static com.flex83.app.constant.MongoDBConstants._ID;

@Service
public class GroupsService {
    @Autowired
    private MongoDBService mongoDBService;
    @Autowired
    private GenerateUtils generateUtils;

    public void createGroups(GroupCreateRequest groupCreateRequest) {
        Document groupDetails = new Document();
        groupDetails.put("id", CommonUtils.generateUUID());
        generateUtils.generateFrom(groupCreateRequest, groupDetails);

        mongoDBService.create(GROUPS_COLLECTION, groupDetails);
    }

    public List<GroupDetails> getAllGroups() {
        List<GroupDetails> response = new ArrayList<>();

        Document query = new Document();
        Document projection = new Document();
        projection.put(_ID, 0);

        List<Document> result = mongoDBService.findList(GROUPS_COLLECTION, query, projection);

        result.forEach(element -> {
            GroupDetails groupDetails = new GroupDetails();
            response.add((GroupDetails) generateUtils.generateFrom(element, groupDetails));
        });

        return response;
    }
}
