package com.example.rensourcevideorentals.enums;

import lombok.Getter;

public enum VideoTypeCategory {
    Regular("Regular", 10d), Childrens_Movie("Children's Movie", 8d), New_Release("New Release", 15d);

    @Getter
    private String description;

    @Getter
    private Double rate;

    VideoTypeCategory(String description, Double rate) {
        this.description = description;
        this.rate = rate;
    }
}
