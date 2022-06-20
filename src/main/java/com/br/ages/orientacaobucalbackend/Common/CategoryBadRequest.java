package com.br.ages.orientacaobucalbackend.Common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class CategoryBadRequest {
    public final String error = "All of 'color', 'name' and 'imageUrl' are required";
}
