package com.sparta.trellowiththreeipeople.user.service;

import com.sparta.trellowiththreeipeople.user.dto.request.CreateUserRequestDto;
import com.sparta.trellowiththreeipeople.user.dto.request.UpdateUserRequestDto;
import com.sparta.trellowiththreeipeople.user.entity.User;
import com.sparta.trellowiththreeipeople.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(CreateUserRequestDto createUserRequestDto) throws BadRequestException {
        User findUser = userRepository.findByUsername(createUserRequestDto.getUsername())
                .orElse(null);

        if (findUser != null) {
            throw new BadRequestException("이미 존재하는 회원입니다");
        }
        String encryptpassword = passwordEncoder.encode(createUserRequestDto.getPassword());
        User saveUser = new User(createUserRequestDto, encryptpassword);
        userRepository.save(saveUser);
    }

    @Transactional
    public void updateUser(UpdateUserRequestDto updateUserRequestDto, User user)
            throws BadRequestException {
        User findUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않은 유저입니다"));

        if (!passwordEncoder.matches(updateUserRequestDto.getPrePassword(), findUser.getPassword())) {
            throw new BadRequestException("비밀번호가 일치 하지 않습니다.");
        }

        String encryptpassword = passwordEncoder.encode(updateUserRequestDto.getPostPassword());
        findUser.update(encryptpassword);
    }
}
