package com.examle.springProject.controller.utility;

import com.examle.springProject.domain.CardType;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ControllerUtils {
    public static void pageNumberCounts(int totalPages , Model model){
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
    public static List<String> getCardTypes(){
        return Stream.of(CardType.values())
                .map(CardType::name)
                .collect(Collectors.toList());
    }
}
