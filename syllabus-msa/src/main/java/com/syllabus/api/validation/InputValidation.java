package com.syllabus.api.validation;

import com.syllabus.api.model.Message;
import org.springframework.http.ResponseEntity;

public class InputValidation {
    public static boolean isNumeric(String str){
        if(!str.matches("-?\\d+(\\.\\d+)?")) return true;
        return false;
    }
}
