package com.shaoxing.lixing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author caishaodong
 * @Date 2020-09-10 19:00
 * @Description 模板下载
 **/
@Controller
@RequestMapping("/download")
public class TemplateDownLoad {
    private static final String ORDER_IMPORT_TEMPLATE = "/res/order_template.xlsx";

    /**
     * 订单导入模板下载
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/orderImportTemplate")
    public void orderImportTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(ORDER_IMPORT_TEMPLATE).forward(request, response);
    }
}
