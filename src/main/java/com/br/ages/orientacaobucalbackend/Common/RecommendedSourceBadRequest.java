package com.br.ages.orientacaobucalbackend.Common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class RecommendedSourceBadRequest {
    public final String error = "Check your payload! link, title and description are required ... also check if your external source url is valid!";
}
