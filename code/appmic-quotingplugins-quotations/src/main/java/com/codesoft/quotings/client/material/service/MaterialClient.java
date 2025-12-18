package com.codesoft.quotings.client.material.service;

import com.codesoft.quotings.client.material.dto.MaterialResponseDto;

public interface MaterialClient {

  MaterialResponseDto searchByName(final String name);
}
