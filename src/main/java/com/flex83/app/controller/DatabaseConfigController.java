package com.flex83.app.controller;

import com.flex83.app.bussiness.DatabaseConfigurationService;
import com.flex83.app.enums.ApiResponseCode;
import com.flex83.app.request.DatabaseConfigRequest;
import com.flex83.app.request.DatabaseConfigUpdateRequest;
import com.flex83.app.response.ApiResponseDTO;
import com.flex83.app.response.generic.AccessDeniedResponseDTO;
import com.flex83.app.response.generic.BadRequestResponseDTO;
import com.flex83.app.response.generic.NotAuthenticatedResponseDTO;
import com.flex83.app.response.generic.ResponseDTO;
import com.flex83.app.response.utils.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "(V1) Tenant Database Configs", tags = {"Flex83 - (V1) Tenant Database Configs"})
@RestController
@RequestMapping(value = "/api/v1/database/configs")
public class DatabaseConfigController {
    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private DatabaseConfigurationService databaseConfigurationService;

    @ApiOperation(value = "Create/create database Configs")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<?> createDatabaseConfig(@RequestBody DatabaseConfigRequest databaseConfigRequest) throws Exception {
        databaseConfigurationService.createDataBaseConfig(databaseConfigRequest);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get all database Configs ")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping
    public ResponseDTO<?> getDatabaseConfig() throws Exception {
        return responseUtil.ok(databaseConfigurationService.getDataBaseConfig(), ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get database Configs by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/{id}")
    public ResponseDTO<?> getDatabaseConfigById(@ApiParam(value = "id",required = true) @PathVariable String id) throws Exception {
        return responseUtil.ok(databaseConfigurationService.getDataBaseConfigById(id), ApiResponseCode.SUCCESS);
    }


    @ApiOperation(value = "Delete database Configs")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseDTO<?> deleteDatabaseConfig(@ApiParam(value = "id",required = true) @PathVariable String id) throws Exception {
        databaseConfigurationService.deleteByIdDataBaseConfig(id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Update database Configs")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PutMapping("/{id}")
    public ResponseDTO<?> updateDatabaseConfig(@ApiParam(value = "databaseConfigUpdateRequest", required = true) @RequestBody DatabaseConfigUpdateRequest databaseConfigUpdateRequest, @ApiParam(value = "id",required = true) @PathVariable("id") String id) throws Exception {
        databaseConfigurationService.updateDataBaseConfig(databaseConfigUpdateRequest,id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }
}
