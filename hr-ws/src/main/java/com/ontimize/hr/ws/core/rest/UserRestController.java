package com.ontimize.hr.ws.core.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ontimize.hr.api.core.service.IUserService;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.server.rest.ORestController;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserRestController extends ORestController<IUserService> {

    @Autowired
    private IUserService userSrv;

    @Override
    public IUserService getService() {
        return this.userSrv;
    }

    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityResult> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityResult> addUser(@RequestBody Map<? super Object, ? super Object> attrMap) {
        return new ResponseEntity<>(userSrv.userInsert(attrMap), HttpStatus.OK);
    }

}
