package com.mwguy.vezdecod_20_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private String id;
    private String title;
    private String content;
    private String categoryId;
    private long timestamp;
}
