package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.mapper.StoreMapper;
import com.ssafy.happyhouse.redis.entity.Store;
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
    private static final String TYPE_KEY_PREFIX = "type:";
    private static final String DONG_TYPE_KEY_PREFIX = "dong:%s:type:%s";

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
            Map<String, Object> storeData = new HashMap<>();
            storeData.put("storeName", store.getStoreName());
            storeData.put("lat", store.getLat());
            storeData.put("lng", store.getLng());
            storeData.put("dongCode", store.getDongCode());
            storeData.put("jibun", store.getJibun());
            storeData.put("doro", store.getDoro());
            redisTemplate.opsForHash().putAll(STORE_KEY_PREFIX+store.getStoreCode(), storeData);

            // store Id를 동에 기반한 set에 저장 (dong:동이름, 상점ID)
            redisTemplate.opsForSet().add(DONG_KEY_PREFIX + store.getDongName(), store.getStoreCode());

            // store ID를 업종에 기반한 set에 저장 (type:G1, 상점ID)
            redisTemplate.opsForSet().add(TYPE_KEY_PREFIX + store.getStoreTypeCode(), store.getStoreTypeCode());

            // store Id를 동과 업종에 기반한 set에 저장 (dong:동이름:type:G1, 상점ID)
            redisTemplate.opsForSet().add(String.format(DONG_TYPE_KEY_PREFIX, store.getDongName(), store.getStoreTypeCode()), store.getStoreCode() );

        }
    }

    public Set<String> getStoreIdsByDong(String dong) {
        log.info(DONG_KEY_PREFIX+dong + "==================================================");
        return redisTemplate.opsForSet().members(DONG_KEY_PREFIX+dong);
    }

    public Set<String> getStoreIdsByType(String type) {
        return redisTemplate.opsForSet().members(TYPE_KEY_PREFIX+type);
    }

    public Set<String> getStoreIdsByDongAndType(String dong, String business) {
        return redisTemplate.opsForSet().members(String.format(DONG_TYPE_KEY_PREFIX, dong, business));
    }

    public List<Store> getStoresByDong(String dong) {
        Set<String> storeIds = getStoreIdsByDong(dong);
        log.info("********************* size " + storeIds.size());
        return getStoresByIds(storeIds);
    }

    public List<Store> getStoresByType(String type) {
        Set<String> storeIds = getStoreIdsByType(type);
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
                        store.setLat((String) storeData.get("lat"));
                        store.setLng((String) storeData.get("lng"));
                        store.setDongCode((String) storeData.get("dongCode"));
                        store.setJibun((String) storeData.get("jibun"));
                        store.setDoro((String) storeData.get("doro"));

                        log.info("Found store for ID: " + id + " - " + store);
                        return store;
                    }
                })
                .filter(Objects::nonNull) // null 값 제거
                .collect(Collectors.toList());
    }




}
