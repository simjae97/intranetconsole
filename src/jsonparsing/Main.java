package jsonparsing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "홍길동");
        data.put("age", 25);
        data.put("address", Map.of(
                "city", "서울",
                "zip", "12345"
        ));
        data.put("hobbies", List.of("축구", "독서", "코딩"));

        // Map을 JSON으로 변환
        String json = mapToJson(data);
        System.out.println(json);
    }

//    public static String mapToJson(Map<String, Object> map) {  //재귀
//        StringBuilder jsonBuilder = new StringBuilder("{");
//
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//            // 키 추가
//            jsonBuilder.append("\"").append(key).append("\":");
//
//            // 값이 Map인 경우 재귀 처리
//            if (value instanceof Map) {
//                jsonBuilder.append(mapToJson((Map<String, Object>) value));
//            }
//            // 값이 List인 경우 처리
//            else if (value instanceof List) {
//                jsonBuilder.append(listToJson((List<Object>) value));
//            }
//            // 값이 문자열이면 따옴표 추가
//            else if (value instanceof String) {
//                jsonBuilder.append("\"").append(value).append("\"");
//            }
//            // 그 외 값 처리 (숫자 등)
//            else {
//                jsonBuilder.append(value);
//            }
//
//            jsonBuilder.append(",");
//        }
//
//        // 마지막 쉼표 제거
//        if (jsonBuilder.length() > 1) {
//            jsonBuilder.setLength(jsonBuilder.length() - 1);
//        }
//
//        jsonBuilder.append("}");
//        return jsonBuilder.toString();
//    }
//
//    // List 데이터를 JSON 문자열로 변환하는 메서드
//    private static String listToJson(List<Object> list) {
//        StringBuilder jsonBuilder = new StringBuilder("[");
//
//        for (Object item : list) {
//            if (item instanceof Map) {
//                jsonBuilder.append(mapToJson((Map<String, Object>) item));
//            } else if (item instanceof List) {
//                jsonBuilder.append(listToJson((List<Object>) item));
//            } else if (item instanceof String) {
//                jsonBuilder.append("\"").append(item).append("\"");
//            } else {
//                jsonBuilder.append(item);
//            }
//            jsonBuilder.append(",");
//        }
//
//        // 마지막 쉼표 제거
//        if (jsonBuilder.length() > 1) {
//            jsonBuilder.setLength(jsonBuilder.length() - 1);
//        }
//
//        jsonBuilder.append("]");
//        return jsonBuilder.toString();
//    }


    public static String mapToJson(Map<String, Object> map) { //스택
        StringBuilder jsonBuilder = new StringBuilder("{");
        Stack<Map.Entry<String, Object>> stack = new Stack<>();

        // Map의 각 항목을 스택에 추가
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            stack.push(entry);
        }

        while (!stack.isEmpty()) {
            Map.Entry<String, Object> entry = stack.pop();
            String key = entry.getKey();
            Object value = entry.getValue();


            // 키 처리
            jsonBuilder.append("\"").append(key).append("\":");

            // 값 처리
            if (value instanceof Map) {
                jsonBuilder.append("{");
                Map<String, Object> nestedMap = (Map<String, Object>) value;
                for (Map.Entry<String, Object> nestedEntry : nestedMap.entrySet()) {
                    stack.push(nestedEntry);
                }
                jsonBuilder.append("},");
            } else if (value instanceof List) {
                jsonBuilder.append(listToJson((List<Object>) value)).append(",");
            } else if (value instanceof String) {
                jsonBuilder.append("\"").append(value).append("\",");
            } else {
                jsonBuilder.append(value).append(",");
            }
        }

        // 마지막 쉼표 제거
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    // List 데이터를 JSON 문자열로 변환
    private static String listToJson(List<Object> list) {
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Object item : list) {
            if (item instanceof Map) {
                jsonBuilder.append(mapToJson((Map<String, Object>) item)).append(",");
            } else if (item instanceof String) {
                jsonBuilder.append("\"").append(item).append("\",");
            } else {
                jsonBuilder.append(item).append(",");
            }
        }

        // 마지막 쉼표 제거
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}