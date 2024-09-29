package com.daisy.daisy_hotel_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "blog_images")
public class BlogImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;
}