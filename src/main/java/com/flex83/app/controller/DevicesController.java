package com.flex83.app.controller;

import com.flex83.app.bussiness.DevicesService;
import com.flex83.app.enums.ApiResponseCode;
import com.flex83.app.request.DeviceCreateRequest;
import com.flex83.app.request.DeviceFilter;
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

import static com.flex83.app.constant.ApplicationConstants.X_TENANT_NAME;

@Api(value = "(V1) Devices Controller", tags = {"Flex83 - (V1) Devices Controller"})
@RestController
@RequestMapping(value = "/api/v1/devices")
public class DevicesController {
    @Autowired
    private ResponseUtil responseUtil;
    @Autowired
    private DevicesService devicesService;

    @ApiOperation(value = "Create devices")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<?> createDevices(@ApiParam(value =X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant, @RequestBody DeviceCreateRequest deviceCreateRequest) throws Exception {
        devicesService.createDevices(deviceCreateRequest);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get all devices")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping(value = "/filter")
    public ResponseDTO<?> getAllDevices(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant, @RequestBody DeviceFilter deviceFilter) throws Exception {
        return responseUtil.ok(devicesService.getAllDevices(deviceFilter), ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class), @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/{id}")
    public ResponseDTO<?> getById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant ,@ApiParam(value = "Enter Device ID", required = true) @PathVariable("id") String id) throws Exception {
        return responseUtil.ok(devicesService.getById(id), ApiResponseCode.SUCCESS);
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
    public ResponseDTO<?> deleteById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant, @ApiParam(value = "Enter Device ID") @PathVariable("id") String id) throws Exception {
        devicesService.deleteById(id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Update by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PutMapping("/{id}")
    public ResponseDTO<?> updateById(@ApiParam(value = X_TENANT_NAME, required = true) @RequestHeader(name = X_TENANT_NAME) String tenant, @ApiParam(value = "deviceCreateRequest", required = true) @RequestBody DeviceCreateRequest deviceCreateRequest,@ApiParam(value = "id",required = true) @PathVariable("id") String id) throws Exception {
        devicesService.updateById(deviceCreateRequest, id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }
}
