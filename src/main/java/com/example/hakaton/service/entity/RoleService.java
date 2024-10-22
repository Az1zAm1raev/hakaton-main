package com.example.hakaton.service.entity;


import com.example.hakaton.dto.entity.RoleDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.List;


public interface RoleService {
    List<RoleDTO> getAll();
    RoleDTO getById (Long id);
    RoleDTO create(RoleDTO command);
    RoleDTO update(RoleDTO command);
    void delete(Long id);
}
