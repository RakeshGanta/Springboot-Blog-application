package com.democracy.social.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2,message = "Post title should have atleast two characters")
    @Column(nullable = false)
    private String title;

    @NotEmpty
    @Size(min = 6,message = "Post title should have atleast six characters")
    @Column(nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY   )
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @Column(nullable = true)
    private String postedBy;

}
