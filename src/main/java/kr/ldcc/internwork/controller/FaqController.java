package kr.ldcc.internwork.controller;

import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class FaqController {

    @PostMapping("/api/faq")
    public Response registerFaq() {
        return Response.ok()
                .setData();
    }

    @GetMapping("/api/faq/{faq_id}")
    public Response searchFaq() {
        return Response.ok()
                .setData();
    }

    @PutMapping("/api/faq/{faq_id}")
    public Response updateFaq() {
        return Response.ok()
                .setData();
    }

    @DeleteMapping("/api/faq/{faq_id}")
    public Response deleteFaq() {
        return Response.ok()
                .setData();
    }
}
