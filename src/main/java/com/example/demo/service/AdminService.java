package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: 4. find or save 예제 개선
    @Transactional
    public List<Long> reportUsers(List<Long> userIds) {
        //한번에 조회 후 조회한 크기와 기존 크기가 다르면 에러 throw
        List<User> users = userRepository.findAllById(userIds);

        if(users.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 ID 값이 있습니다.");
        }

        for(User user : users) {
            user.updateStatusToBlocked();
        }

        userRepository.saveAll(users);

        return userIds;
    }
}
