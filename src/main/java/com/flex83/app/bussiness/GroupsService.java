package com.flex83.app.bussiness;

import com.flex83.app.request.GroupCreateRequest;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.CommonUtils;
import com.flex83.app.utils.GenerateUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.flex83.app.constant.MongoDBConstants.GROUPS_COLLECTION;

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
}
