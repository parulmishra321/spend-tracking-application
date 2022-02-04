package com.flex83.app.controller;

import com.flex83.app.bussiness.GroupsService;
import com.flex83.app.enums.ApiResponseCode;
import com.flex83.app.request.GroupCreateRequest;
import com.flex83.app.response.ApiResponseDTO;
import com.flex83.app.response.generic.AccessDeniedResponseDTO;
import com.flex83.app.response.generic.BadRequestResponseDTO;
import com.flex83.app.response.generic.NotAuthenticatedResponseDTO;
import com.flex83.app.response.generic.ResponseDTO;
import com.flex83.app.response.utils.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flex83.app.constant.ApplicationConstants.X_TENANT_NAME;

@Api(value = "(V1) Groups Controller", tags = {"Flex83 - (V1) Groups Controller"})
@RestController
@RequestMapping(value = "/api/v1/groups")
public class GroupsController {
    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private GroupsService groupsService;

    @ApiOperation(value = "Create groups")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<?> createGroups(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant, @RequestBody GroupCreateRequest groupCreateRequest) throws Exception {
        groupsService.createGroups(groupCreateRequest);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get all groups")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })

    @GetMapping
    public ResponseDTO<?> getAllGroups(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant) throws Exception {
        return responseUtil.ok(groupsService.getAllGroups(), ApiResponseCode.SUCCESS);
    }
    @ApiOperation(value = "Get by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })

    @GetMapping("/{id}")
    public ResponseDTO<?> getById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant ,@ApiParam(value = "groupId",required = true) @PathVariable("id") String id) throws Exception {
        return responseUtil.ok(groupsService.getById(id), ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Delete by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
   @DeleteMapping("/{id}")
    public ResponseDTO<?> deleteById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant ,@PathVariable("id") String id) throws Exception {
        groupsService.deleteById(id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "update by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PutMapping("/{id}")
    public ResponseDTO<?> updateById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant ,@ApiParam(value = "groupCreateRequest",required = true) @RequestBody GroupCreateRequest groupCreateRequest,@ApiParam(value = "id",required = true) @PathVariable("id") String id) throws Exception {
        groupsService.updateById(groupCreateRequest,id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }
}
