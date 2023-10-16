package com.mjc.school.repository;

import com.mjc.school.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Modifying
    @Query(value = """
            DELETE
            FROM news
            WHERE authors_id = :authors_id
            """, nativeQuery = true)
    void deleteByAuthorId(@Param("authors_id") Long authorId);

    @Modifying
    @Query(value = """
            DELETE
            FROM news_tags
            WHERE news_id = :news_id
            """, nativeQuery = true)
    void deleteAllTagsFromNewsByNewsId(@Param("news_id") Long newsId);

    @Modifying
    @Query(value = """
            UPDATE news
            SET title = :title,
                content = :content,
                authors_id = :authors_id,
                modified = :modified
            WHERE id = :news_id
             """, nativeQuery = true)
    void update(@Param("title") String title,
                @Param("content") String content,
                @Param("authors_id") Long authorId,
                @Param("modified") String modified,
                @Param("news_id") Long newsId);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE
                news_tags.tags_id = tags.id
                AND tags.name = :tag_name
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByTagName(@Param("tag_name") String tagName,
                             @Param("indexFirstElement") Integer indexFirstElement,
                             @Param("size") Integer size);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN news_tags
                    ON news.id = news_tags.news_id
                INNER JOIN tags
                    ON news_tags.tags_id = tags.id
            WHERE
                news_tags.tags_id = tags.id
                AND tags.name = :tag_name
            """, nativeQuery = true)
    List<News> findByTagName(@Param("tag_name") String tagName);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
             FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
             WHERE
                 news_tags.tags_id = tags.id
                 AND tags.id = :tag_id;
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByTagId(@Param("tag_id") Long tagId,
                           @Param("indexFirstElement") Integer indexFirstElement,
                           @Param("size") Integer size);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
             FROM news
                 INNER JOIN news_tags
                     ON news.id = news_tags.news_id
                 INNER JOIN tags
                     ON news_tags.tags_id = tags.id
             WHERE
                 news_tags.tags_id = tags.id
                 AND tags.id = :tag_id
            """, nativeQuery = true)
    List<News> findByTagId(@Param("tag_id") Long tagId);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByPartOfAuthorName(@Param("part_author_name") String partOfAuthorName,
                                      @Param("indexFirstElement") Integer indexFirstElement,
                                      @Param("size") Integer size);

    @Query(value = """
            SELECT news.id, news.title, news.content, news.authors_id,
            news.created, news.modified
            FROM news
                INNER JOIN authors
                    ON news.authors_id = authors.id
            WHERE authors.name LIKE :part_author_name
            """, nativeQuery = true)
    List<News> findByPartOfAuthorName(@Param("part_author_name") String partOfAuthorName);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :author_id
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByAuthorId(@Param("author_id") Long authorId,
                              @Param("indexFirstElement") Integer indexFirstElement,
                              @Param("size") Integer size);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE authors_id = :author_id
            """, nativeQuery = true)
    List<News> findByAuthorId(@Param("author_id") Long authorId);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title = :part_of_title
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByPartOfTitle(@Param("part_of_title") String partOfTitle,
                                 @Param("indexFirstElement") Integer indexFirstElement,
                                 @Param("size") Integer size);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title = :part_of_title
            """, nativeQuery = true)
    List<News> findByPartOfTitle(@Param("part_of_title") String partOfTitle);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE content = :part_of_content
            LIMIT :size OFFSET :indexFirstElement
            """, nativeQuery = true)
    List<News> findByPartOfContent(@Param("part_of_content") String partOfContent,
                                   @Param("indexFirstElement") Integer indexFirstElement,
                                   @Param("size") Integer size);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE content = :part_of_content
            """, nativeQuery = true)
    List<News> findByPartOfContent(@Param("part_of_content") String partOfContent);

    @Query(value = """
            SELECT id, title, content, authors_id, created, modified
            FROM news
            WHERE title = :title
            """, nativeQuery = true)
    Optional<News> findNewsByTitle(@Param("title") String title);
}