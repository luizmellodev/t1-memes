package com.br.ages.orientacaobucalbackend.Common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class QuestionBadRequest {
    public final String error = "There must be at least 2 alternatives and at most 3";
}
