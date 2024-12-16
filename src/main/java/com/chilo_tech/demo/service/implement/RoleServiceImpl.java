package com.chilo_tech.demo.service.implement;

import com.chilo_tech.demo.entity.Role;
import com.chilo_tech.demo.repository.RoleRepository;
import com.chilo_tech.demo.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role creer(Role role) {
        return roleRepository.save(role);
    }

}


