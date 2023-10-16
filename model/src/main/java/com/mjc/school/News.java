package com.mjc.school;

import com.mjc.school.abstr.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, length = 50, unique = true)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "authors_id")
    private Author author;

    @Column(name = "created", nullable = false)
    private String created;

    @Column(name = "modified", nullable = false)
    private String modified;

    @OneToMany(mappedBy = "news", cascade = ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "news", cascade = ALL)
    private List<NewsTag> tags;

    public News() {
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }
}