package com.amazon.UserService.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.amazon.UserService.dto.UserRequestDTO;
import com.amazon.UserService.dto.UserResponseDTO;
import com.amazon.UserService.entity.Users;
import com.amazon.UserService.exception.DuplicateResourceException;
import com.amazon.UserService.exception.ResourceNotFoundException;
import com.amazon.UserService.repository.UserRepository;
import com.amazon.UserService.service.UserService;
import com.amazon.UserService.utils.UserRole;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already in use");
        }
        Users user = modelMapper.map(dto, Users.class);
        user.setUserRole(UserRole.valueOf(dto.getUserRole()));
        Users saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        modelMapper.map(dto, user);
        user.setUserRole(UserRole.valueOf(dto.getUserRole()));

        Users updated = userRepository.save(user);
        return modelMapper.map(updated, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean validateUser(Long id) {
        return userRepository.existsById(id);
    }
}
