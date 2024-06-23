package com.miniproject.demo.domain.chatroom.repository;

import com.miniproject.demo.domain.mapping.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends JpaRepository<UserChatRoom, Long> {
}
