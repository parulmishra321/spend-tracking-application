package com.spendTracker.app.controller;

import com.spendTracker.app.enums.ApiResponseCode;
import com.spendTracker.app.request.DeviceTypeCreateRequest;
import com.spendTracker.app.response.ApiResponseDTO;
import com.spendTracker.app.response.generic.AccessDeniedResponseDTO;
import com.spendTracker.app.response.generic.BadRequestResponseDTO;
import com.spendTracker.app.response.generic.NotAuthenticatedResponseDTO;
import com.spendTracker.app.response.generic.ResponseDTO;
import com.spendTracker.app.response.utils.ResponseUtil;
import com.spendTracker.app.bussiness.DeviceTypeServices;
import com.spendTracker.app.constant.ApplicationConstants;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "(V1) Device Type Controller", tags = {"SpendTracker - (V1) Device Type Controller"})
@RestController
@RequestMapping(value = "/api/v1/deviceType")
public class DeviceTypeController {
    @Autowired
    private DeviceTypeServices deviceTypeServices;
    @Autowired
    private ResponseUtil responseUtil;

    @ApiOperation(value = "Create device-type")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<?> createDeviceType(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "deviceTypeCreateRequest", required = true) @RequestBody @Valid DeviceTypeCreateRequest deviceTypeCreateRequest) throws Exception {
        deviceTypeServices.createDeviceType(deviceTypeCreateRequest);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get all device-type")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping
    public ResponseDTO<?> getAllDeviceType(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant) throws Exception {
        return responseUtil.ok(deviceTypeServices.getAllDeviceType(), ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Get device-type by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @GetMapping("/{id}")
    public ResponseDTO<?> getDeviceTypeById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "device type id", required = true) @PathVariable(name = "id") String id) throws Exception {
        return responseUtil.ok(deviceTypeServices.getDeviceTypeById(id), ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "Update device-type by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @PutMapping("/{id}")
    public ResponseDTO<?> updateDeviceTypeById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "device type id", required = true) @PathVariable(name = "id") String id, @RequestBody @Valid DeviceTypeCreateRequest deviceTypeCreateRequest) throws Exception {
        deviceTypeServices.updateDeviceTypeById(id, deviceTypeCreateRequest);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }

    @ApiOperation(value = "delete device-type by id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ApiResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponseDTO.class),
            @ApiResponse(code = 401, message = "You are Not Authenticated", response = NotAuthenticatedResponseDTO.class),
            @ApiResponse(code = 403, message = "Not Authorized on this resource", response = AccessDeniedResponseDTO.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseDTO<?> deleteDeviceTypeById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "device type id", required = true) @PathVariable(name = "id") String id) throws Exception {
        deviceTypeServices.deleteDeviceTypeById(id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }
}
