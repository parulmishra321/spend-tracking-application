package com.flex83.app.bussiness;

import com.flex83.app.entities.TenantDatabaseConfig;
import com.flex83.app.exception.ValidationException;
import com.flex83.app.request.GroupCreateRequest;
import com.flex83.app.response.GroupDetails;
import com.flex83.app.services.MongoDBService;
import com.flex83.app.utils.CommonUtils;
import com.flex83.app.utils.GenerateUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Document getById(String id){
        Document query=new Document();
        query.put("id",id);
        Document projection=new Document();
        projection.put(_ID,0);

        Document result=mongoDBService.findOne(GROUPS_COLLECTION,query,projection);
        if(Objects.isNull(result) || result.isEmpty()){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(),"Group details does not exists");

        }
        return result;
    }
    public void deleteById(String id){
        Document query=new Document();
        query.put("id",id);

       if(id.isEmpty()){
           throw new ValidationException(HttpStatus.BAD_REQUEST.value(),"this document does not exists");
       }else {
           mongoDBService.deleteOne(GROUPS_COLLECTION,query);
       }
    }
    public void updateDocument(GroupCreateRequest groupCreateRequest,String id){
        Document filter=new Document();
        filter.put("id",id);
        Document set=new Document();
        set.put(_ID,0);
       if(groupCreateRequest.getName()!=id || groupCreateRequest.getDescription()!=id){
           mongoDBService.update(GROUPS_COLLECTION, filter, set);
       }
       else {
           throw  new ValidationException(HttpStatus.BAD_REQUEST.value(), "If your id is equal to the privious name or discription");
       }
    }
}
