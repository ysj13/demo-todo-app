package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String test() {
        return "test";
    }

    /**
     * 1. Pathvariable 을 이용한 매개변수 전달
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String testId(@PathVariable(required = false) int id) {
        return "test = " + id;
    }

    /**
     * 복잡한 자료형을 통째로 요청에 보내고 싶은 경우
     * @param testRequestBodyDTO
     * @return
     */
    @GetMapping("/testRequestBody")
    public String testRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "testRequestBody = " + testRequestBodyDTO.toString();
    }

    /**
     * 복잡한 자료형을 통째로 응답에 보내고 싶은 경우
     * @return
     */
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }
}
