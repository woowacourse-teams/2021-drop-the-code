package com.wootech.dropthecode.dto.response;

public class ProfileResponse {
    /**
     * 프로필 id
     */
    private Long id;
    /**
     * 유저 닉네임
     */
    private String name;
    /**
     * 유저 github 프로필 이미지 링크
     */
    private String imageUrl;

    public ProfileResponse() {
    }

    public ProfileResponse(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static ProfileResponse of(Long id, String name, String imageUrl) {
        return new ProfileResponse(id, name, imageUrl);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
