package com.example.main.Constants;
public class RedisConstants {
    public static final Long CACHE_NULL_TTL = 2L;
    // hours
    public static final Long CACHE_BOOK_NUMBER_TTL = 24L;
    public static final String CACHE_BOOKS_REFIX = "books:";
    public static final String CACHE_BOOKS_NUMBER_KEY_PREFIX = "books:number:";
    public static final String CACHE_BOOKS_BOOK_KEY_PREFIX = "books:book:";
    public static final Long CACHE_BOOK_TTL = 30L;
    public static final Long CACHE_VIEW_TTL = 30L;
    public static final String CACHE_EVENT_KEY = "cache:event:";
    public static final String CACHE_VIEW_KEY = "cache:view:";
    //    public static final Long abnormalEventUUID = -101L;
//    public static final EventDto abnormalEventDto = EventDto.builder()
//        .uuid(abnormalEventUUID)
//        .build();
    public static final String CACHE_USER_EVENT_KEY = "cache:user:event:all:";
    public static final String CACHE_USER_EVENT_NOT_DELETE_KEY = "cache:user:event:active:";
}
