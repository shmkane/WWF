package com.shmkane.wwf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RequestController {

    @GetMapping(value = "/webpageRequest")
    public String webpageRequestForm(Model model) {
        model.addAttribute("webpageRequest", new WebpageRequest());
        return "webpageRequest";
    }

    @PostMapping("/webpageRequest")
    public String webpageRequestForm(@ModelAttribute WebpageRequest webpageRequest) {
        try {
            Document doc = Jsoup.connect(webpageRequest.getUrl()).get();
            String result = doc.body().text().replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
            String results[] = result.split(" ");

            HashMap<String, Integer> hs = new HashMap<String, Integer>();

            for (int i = 0; i < results.length; i++) {
                String str = results[i];
                if (str.length() > 20 || str.equals("")) {
                    continue;
                }
                if (hs.containsKey(str)) {
                    if (webpageRequest.isSorted()) {
                        hs.put(str, hs.get(str) + 1);
                    }
                    continue;
                } else {
                    hs.put(str, 1);
                }

            }

            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(hs.toString());
            if (webpageRequest.isSorted())
                hs = sortByValue(hs);

            if (webpageRequest.getFormattingType().equalsIgnoreCase("None")) {
                if (webpageRequest.isWordOnly()) {
                    for (String name : hs.keySet()) {
                        stringBuilder.append(name).append(" ");
                    }
                    webpageRequest.setResults(stringBuilder.toString());
                } else {
                    webpageRequest.setResults(hs.toString());
                }
                return "result";

            } else if (webpageRequest.getFormattingType().equalsIgnoreCase("Comma")) {

                for (String name : hs.keySet()) {
                    stringBuilder.append(name);
                    if (!webpageRequest.isWordOnly()) {
                        stringBuilder.append("-").append(hs.get(name).toString());
                    }
                    stringBuilder.append(",");
                }
                webpageRequest.setResults(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
                return "result";

            } else if (webpageRequest.getFormattingType().equalsIgnoreCase("newLines")) {

                for (String name : hs.keySet()) {
                    stringBuilder.append(name);
                    if (!webpageRequest.isWordOnly()) {
                        stringBuilder.append("-").append(hs.get(name).toString());
                    }
                    stringBuilder.append("\n");
                }
                webpageRequest.setResults(stringBuilder.toString());
                return "result";

            } else {
                if (webpageRequest.isWordOnly()) {
                    for (String name : hs.keySet()) {
                        stringBuilder.append(name).append(" ");
                    }
                    webpageRequest.setResults(stringBuilder.toString());
                } else {
                    webpageRequest.setResults(hs.toString());
                }
                return "result";
            }


        } catch (Exception ex) {
            return "webpageRequest";
        }
    }

    private static HashMap<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
        HashMap<String, Integer> sortedMap =
                unsortedMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));

        return sortedMap;
    }

}