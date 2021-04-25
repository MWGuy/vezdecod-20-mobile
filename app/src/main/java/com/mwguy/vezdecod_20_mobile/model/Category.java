package com.mwguy.vezdecod_20_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private String id;
    private String title;
    private ContentType contentType;
}
