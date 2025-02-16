package com.juaracoding.servatelthymeleaf.util;

import java.util.List;
import java.util.Map;

public class GenerateStringMenu {

    private StringBuilder sBuilder = new StringBuilder();

    public String stringMenu(List<Map<String,Object>> lt){
        sBuilder.setLength(0);
        for (Map<String, Object> menu : lt) {
            sBuilder.append("<li class=\"nav-item\"><a class=\"nav-link\" href=\"")
                    .append(menu.get("path")) // Ambil path dari menu
                    .append("\">")
                    .append(menu.get("name")) // Ambil nama menu
                    .append("</a></li>");
        }
        return sBuilder.toString();
    }
}