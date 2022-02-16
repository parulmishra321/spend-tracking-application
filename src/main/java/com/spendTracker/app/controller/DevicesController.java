package com.spendTracker.app.controller;

import com.spendTracker.app.bussiness.DevicesService;
import com.spendTracker.app.enums.ApiResponseCode;
import com.spendTracker.app.request.DeviceCreateRequest;
import com.spendTracker.app.request.DeviceFilter;
import com.spendTracker.app.response.ApiResponseDTO;
import com.spendTracker.app.response.generic.AccessDeniedResponseDTO;
import com.spendTracker.app.response.generic.BadRequestResponseDTO;
import com.spendTracker.app.response.generic.NotAuthenticatedResponseDTO;
import com.spendTracker.app.response.generic.ResponseDTO;
import com.spendTracker.app.response.utils.ResponseUtil;
import com.spendTracker.app.constant.ApplicationConstants;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "(V1) Devices Controller", tags = {"SpendTracker - (V1) Devices Controller"})
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
    public ResponseDTO<?> createDevices(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @RequestBody DeviceCreateRequest deviceCreateRequest) throws Exception {
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
    public ResponseDTO<?> getAllDevices(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @RequestBody DeviceFilter deviceFilter) throws Exception {
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
    public ResponseDTO<?> getById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant , @ApiParam(value = "Enter Device ID", required = true) @PathVariable("id") String id) throws Exception {
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
    public ResponseDTO<?> deleteById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "Enter Device ID") @PathVariable("id") String id) throws Exception {
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
    public ResponseDTO<?> updateById(@ApiParam(value = ApplicationConstants.X_TENANT_NAME, required = true) @RequestHeader(name = ApplicationConstants.X_TENANT_NAME) String tenant, @ApiParam(value = "deviceCreateRequest", required = true) @RequestBody DeviceCreateRequest deviceCreateRequest, @ApiParam(value = "id",required = true) @PathVariable("id") String id) throws Exception {
        devicesService.updateById(deviceCreateRequest, id);
        return responseUtil.ok(ApiResponseCode.SUCCESS);
    }
}
