package com.br.ages.orientacaobucalbackend.Common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class AlternativeBadRequest {
    public final String error = "Seems like the critical level chosen is not permitted! Also check if alternativeText is present!";
}
