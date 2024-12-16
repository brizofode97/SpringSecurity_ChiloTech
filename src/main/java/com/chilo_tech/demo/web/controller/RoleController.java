package com.chilo_tech.demo.web.controller;

import com.chilo_tech.demo.entity.Role;
import com.chilo_tech.demo.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService iRoleService;

    @PostMapping(path = "/creation")
    public Role createRole(@RequestBody Role role) {
        return iRoleService.creer(role);
    }

}
