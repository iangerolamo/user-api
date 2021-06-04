package io.github.iangerolamo.userapi.service;

import io.github.iangerolamo.userapi.dto.UserDTO;
import io.github.iangerolamo.userapi.model.User;
import io.github.iangerolamo.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDTO::convert).collect(Collectors.toList());
    }

    public UserDTO findById(long userId) {
        Optional<User> usuario = userRepository.findById(userId);
        if (usuario.isEmpty()) {
            return null;
        }
        return UserDTO.convert(usuario.get());
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    public UserDTO delete(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
        return null;
    }

    public UserDTO findByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf);
        if (user != null) {
            return UserDTO.convert(user);
        }
        return null;
    }

    public List<UserDTO> queryByName(String nome) {
        List<User> usuarios = userRepository.queryByNomeLike(nome);
        return usuarios.stream().map(UserDTO::convert).collect(Collectors.toList());
    }
}
