package com.examle.springProject.controller.utility;

import com.examle.springProject.domain.CardType;
import org.springframework.data.domain.Sort;
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
    public static Sort getSort(String sortBy , String nameBy , Model model){
        Sort sort = null;
        if(nameBy != null && !nameBy.isEmpty()){
            sort = Sort.by(getSortType(sortBy), nameBy);
            model.addAttribute("sort", sortBy);
            model.addAttribute("nameBy", nameBy);
        }
            return sort;
        }
    public static Sort.Direction getSortType(String type){
        return type == null || type.isEmpty() ? Sort.DEFAULT_DIRECTION : Sort.Direction.valueOf(type);
    }
}
