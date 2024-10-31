package com.example.author.constants;

public class RedisConstants {
    public static final Long CACHE_NULL_TTL = 2L;
    // hours
    public static final Long CACHE_BOOK_NUMBER_TTL = 24L;
    public static final String CACHE_BOOKS_PREFIX = "books:";
    public static final String CACHE_BOOKS_AUTHOR_KEY_PREFIX = "books:author:";
    public static final String CACHE_BOOKS_NUMBER_KEY_PREFIX = "books:number:";
    public static final String CACHE_BOOKS_BOOK_KEY_PREFIX = "books:book:";
    public static final Long CACHE_BOOK_TTL = 30L;

}
