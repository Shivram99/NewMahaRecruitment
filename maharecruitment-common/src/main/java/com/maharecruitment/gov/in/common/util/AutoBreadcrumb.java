package com.maharecruitment.gov.in.common.util;


import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class AutoBreadcrumb {

    private static final Map<String, String> LABEL_MAP = new LinkedHashMap<>();

    static {
        LABEL_MAP.put("eservicebook", "E-Service Book");
        LABEL_MAP.put("attendance", "Attendance");
        LABEL_MAP.put("payroll", "Payroll");
        LABEL_MAP.put("master", "Master Module");
        LABEL_MAP.put("employee", "Employee");
        LABEL_MAP.put("establishment", "Establishment");
        LABEL_MAP.put("leave", "Leave Management");
        LABEL_MAP.put("pension", "Pension");
        LABEL_MAP.put("loans", "Loans");
        LABEL_MAP.put("dms", "Document Management");
        LABEL_MAP.put("tp", "Transfer Posting");
        LABEL_MAP.put("promotion", "Promotion");
        LABEL_MAP.put("common", "Common Module");
        LABEL_MAP.put("hrms", "HRMS Portal");
        // Add more if needed
    }

    public static Map<String, String> generate(HttpServletRequest request) {
        String path = request.getRequestURI();

        Map<String, String> breadcrumb = new LinkedHashMap<>();
        breadcrumb.put("Home", "/");

        String[] parts = path.split("/");

        String fullPath = "";

        for (String part : parts) {
            if (part.isEmpty()) continue;

            fullPath += "/" + part;

            breadcrumb.put(getLabel(part), fullPath);
        }

        return breadcrumb;
    }

    private static String getLabel(String segment) {
        // First check if we have a mapped user-friendly label
        if (LABEL_MAP.containsKey(segment.toLowerCase())) {
            return LABEL_MAP.get(segment.toLowerCase());
        }

        // Otherwise convert: salary-setup → Salary Setup
        segment = segment.replace("-", " ");
        return segment.substring(0, 1).toUpperCase() + segment.substring(1);
    }
}
