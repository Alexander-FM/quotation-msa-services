package com.codesoft.employees.user.utils;

import com.codesoft.employees.user.dto.request.UserRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

  public static void encodePassword(final UserRequestDto requestDto, final BCryptPasswordEncoder passwordEncoder) {
    requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
  }
}
