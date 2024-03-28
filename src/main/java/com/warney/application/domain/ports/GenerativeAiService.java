package com.warney.application.domain.ports;

public interface GenerativeAiService {

    String generativeContent(String objective, String context);
}
