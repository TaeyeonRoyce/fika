package com.wefly.fika.service.impl;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.wefly.fika.config.response.CustomException;
import com.wefly.fika.domain.data.SpotData;
import com.wefly.fika.domain.data.SpotMenu;
import com.wefly.fika.domain.drama.Drama;
import com.wefly.fika.domain.member.Member;
import com.wefly.fika.exception.NoSuchDataFound;
import com.wefly.fika.repository.DramaRepository;
import com.wefly.fika.repository.MemberRepository;
import com.wefly.fika.repository.ReviewRepository;
import com.wefly.fika.repository.SpotDataRepository;
import com.wefly.fika.repository.SpotMenuRepository;
import com.wefly.fika.service.ISpotDataService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpotDataServiceTest {

}