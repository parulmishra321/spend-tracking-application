package com.flex83.app.bussiness;

import com.flex83.app.entities.TenantDatabaseConfig;
import com.flex83.app.exception.ValidationException;
import com.flex83.app.request.GroupCreateRequest;
import com.flex83.app.response.GroupDetails;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.CommonUtils;
import com.flex83.app.utils.GenerateUtils;
import com.flex83.app.utils.ValidationUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.flex83.app.constant.MongoDBConstants.*;

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

    public Document getById(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projection = new Document();
        projection.put(_ID, 0);
        Document result = mongoDBService.findOne(GROUPS_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group details does not exists");
        }
        return result;
    }

    public void deleteById(String id) {
        Document query = new Document();
        query.put("id", id);
        Document projection = new Document();
        projection.put(_ID, 0);
        Document result = mongoDBService.findOne(GROUPS_COLLECTION, query, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group details does not exists");
        }
        mongoDBService.deleteOne(GROUPS_COLLECTION, query);
    }

    public void updateById(GroupCreateRequest groupCreateRequest, String id) {
        Document projection = new Document();
        projection.put(_ID, 0);
        Document result = mongoDBService.findById(GROUPS_COLLECTION, id, projection);
        if (Objects.isNull(result) || result.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "group details does not exists");
        }
        Document set=new Document();
        if (!result.getString("name").equalsIgnoreCase(groupCreateRequest.getName())) {
            Document params = new Document();
            params.put("name", groupCreateRequest.getName());
            Document groupInfo = mongoDBService.findOne(GROUPS_COLLECTION, params, projection);
            if (ValidationUtils.nonNullOrEmpty(groupInfo)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Group already exists with this name");
            }
            set.put("name",groupCreateRequest.getName());
        }
        set.put("description",ValidationUtils.nonNullOrEmpty(groupCreateRequest.getDescription())? groupCreateRequest.getDescription():result.getString("description"));
        mongoDBService.update(GROUPS_COLLECTION, new Document(ID,id), set);
    }
}
