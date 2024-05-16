package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.mapper.StoreMapper;
import com.ssafy.happyhouse.redis.entity.Store;
import com.ssafy.happyhouse.request.AddressName;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final LettuceConnectionFactory connectionFactory;
    private final StoreMapper storeMapper;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String STORE_KEY_PREFIX = "store:";
    private static final String DONG_KEY_PREFIX = "dong:";
    private static final String CATEGORY_KEY_PREFIX = "category:";
    private static final String DONG_CATEGORY_KEY_PREFIX = "dong:%s:category:%s";

    @PostConstruct
    public void init() {
        //loadDataFromMysqlToRedis();
    }

    @PreDestroy
    public void cleanUp() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (RedisConnection connection = connectionFactory.getConnection()) {
                connection.flushDb();
                System.out.println("레디스 데이터 초기화");
            }

        }));
    }

    // 서비스 초기에 상권분석 데이터를 Redis에 넣는다.
    public void loadDataFromMysqlToRedis() {
        List<Store> stores = storeMapper.findALl();

        for(Store store : stores) {
            // 필요한 데이터 설정
            Map<String, Object> storeData = new HashMap<>();
            storeData.put("storeName", store.getStoreName());
            storeData.put("mainCategoryName", store.getMainCategoryName());
            storeData.put("doro", store.getDoro());
            storeData.put("lat", store.getLat());
            storeData.put("lng", store.getLng());

            redisTemplate.opsForHash().putAll(STORE_KEY_PREFIX+store.getStoreCode(), storeData);

            // store Id를 동에 기반한 set에 저장 (dong:동코드, 상점ID)
            redisTemplate.opsForSet().add(DONG_KEY_PREFIX + store.getDongCode(), store.getStoreCode());

            // store ID를 업종에 기반한 set에 저장 (type:G1, 상점ID)
            redisTemplate.opsForSet().add(CATEGORY_KEY_PREFIX + store.getSubCategoryCode(), store.getStoreCode());

            // store Id를 동과 업종에 기반한 set에 저장 (dong:동코드:type:G1, 상점ID)
            redisTemplate.opsForSet().add(String.format(DONG_CATEGORY_KEY_PREFIX, store.getDongCode(), store.getMainCategoryCode()), store.getStoreCode() );

        }
    }

    public Set<String> getStoreIdsByDongCode(String dongCode) {
        log.info(DONG_KEY_PREFIX+dongCode + "==================================================");
        return redisTemplate.opsForSet().members(DONG_KEY_PREFIX+dongCode);
    }

    public Set<String> getStoreIdsByCategoryCode(String categoryCode) {
        return redisTemplate.opsForSet().members(CATEGORY_KEY_PREFIX+categoryCode);
    }

    public Set<String> getStoreIdsByDongCodeAndType(String dongCode, String category) {
        return redisTemplate.opsForSet().members(String.format(DONG_CATEGORY_KEY_PREFIX, dongCode, category));
    }

    public List<Store> getStoresByDong(AddressName addressName) {

        // 동코드를 받아온다.
        String dongCode = storeMapper.findDongCodeByDongName(addressName);

        // redis에서 현재 지역의 상점 id를 가져온다.
        Set<String> storeIds = getStoreIdsByDongCode(dongCode);
        log.info("********************* size " + storeIds.size());
        return getStoresByIds(storeIds);
    }

    public List<Store> getStoresByCategoryCode(String categoryCode) {
        Set<String> storeIds = getStoreIdsByCategoryCode(categoryCode);
        return getStoresByIds(storeIds);
    }

    private List<Store> getStoresByIds(Set<String> storeIds) {
        return storeIds.stream()
                .map(id -> {
                    String storeKey = STORE_KEY_PREFIX + id;
                    Map<Object, Object> storeData = redisTemplate.opsForHash().entries(storeKey);

                    if (storeData.isEmpty()) {
                        log.info("Store not found for ID: " + id);
                        return null;
                    } else {
                        Store store = new Store();
                        store.setStoreCode(id);
                        store.setStoreName((String) storeData.get("storeName"));
                        store.setMainCategoryName((String) storeData.get("mainCategoryName"));
                        store.setDoro((String) storeData.get("doro"));
                        store.setLat((String) storeData.get("lat"));
                        store.setLng((String) storeData.get("lng"));

                        log.info("Found store for ID: " + id + " - " + store);
                        return store;
                    }
                })
                .filter(Objects::nonNull) // null 값 제거
                .collect(Collectors.toList());
    }




}
