package com.br.ages.orientacaobucalbackend.Common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ContentBadRequest {
    public final String error = "Check your payload! title and textUrl are required ... also check if your video url is valid!";
}
