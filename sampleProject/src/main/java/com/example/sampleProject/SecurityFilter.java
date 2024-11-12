package com.example.sampleProject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityFilter {
    public static List<Security> filterAndSortSecurities(String jsonString, String qualifier) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Security> securities = mapper.readValue(jsonString, new TypeReference<List<Security>>() {});

        return securities.stream()
                .filter(security -> {
                    try {
                        Date maturityDate = new SimpleDateFormat("yyyy-MM-dd").parse(security.getMaturityDate());
                        String[] conditions = qualifier.split("\\|");
                        for (String condition : conditions) {
                            String[] subConditions = condition.trim().replace("(", "").replace(")", "").split("&");
                            boolean matches = true;
                            for (String subCondition : subConditions) {
                                String[] parts = subCondition.trim().split(" ");
                                Date conditionDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1].replace("'", ""));
                                switch (parts[0]) {
                                    case "<=":
                                        matches = matches && !maturityDate.after(conditionDate);
                                        break;
                                    case ">=":
                                        matches = matches && !maturityDate.before(conditionDate);
                                        break;
                                }
                            }
                            if (matches) {
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .sorted((s1, s2) -> {
                    try {
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(s1.getMaturityDate());
                        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(s2.getMaturityDate());
                        return date1.compareTo(date2);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        String jsonString = "["
                + "{\"cusip\": null, \"secSID\": 1, \"issueFactor\": 1, \"commonCode\": \"1_commonCode\", \"blockedBy\": \"1_blockedBy\", \"minorType\": \"1_minorType\", \"shortcode\": \"1_shortcode\", \"sedol\": \"1_sedol\", \"ric\": \"1_ric\", \"descr\": \"1_descr\", \"valueFactor\": 0, \"quickcode\": \"1_quickcode\", \"securityType\": \"1_securityType\", \"maturityDate\": \"2031-03-05\", \"ccy\": \"1_ccy\", \"reservedBy\": \"1_reservedBy\", \"tradedIND\": \"Y\", \"ctryId\": \"1_ctryId\", \"wertpapierCode\": \"1_wertpapierCode\", \"isin\": \"1_ISIN\"},"
                + "{\"cusip\": null, \"secSID\": 2, \"issueFactor\": 1, \"commonCode\": \"2_commonCode\", \"blockedBy\": \"2_blockedBy\", \"minorType\": \"2_minorType\", \"shortcode\": \"2_shortcode\", \"sedol\": \"2_sedol\", \"ric\": \"2_ric\", \"descr\": \"2_descr\", \"valueFactor\": 0, \"quickcode\": \"2_quickcode\", \"securityType\": \"2_securityType\", \"maturityDate\": \"2027-03-05\", \"ccy\": \"2_ccy\", \"reservedBy\": \"2_reservedBy\", \"tradedIND\": \"Y\", \"ctryId\": \"2_ctryId\", \"wertpapierCode\": \"2_wertpapierCode\", \"isin\": \"2_ISIN\"},"
                + "{\"cusip\": null, \"secSID\": 6, \"issueFactor\": 1, \"commonCode\": \"6_commonCode\", \"blockedBy\": \"6_blockedBy\", \"minorType\": \"6_minorType\", \"shortcode\": \"6_shortcode\", \"sedol\": \"6_sedol\", \"ric\": \"6_ric\", \"descr\": \"6_descr\", \"valueFactor\": 0, \"quickcode\": \"6_quickcode\", \"securityType\": \"6_securityType\", \"maturityDate\": \"2019-12-06\", \"ccy\": \"6_ccy\", \"reservedBy\": \"6_reservedBy\", \"tradedIND\": \"Y\", \"ctryId\": \"6_ctryId\", \"wertpapierCode\": \"6_wertpapierCode\", \"isin\": \"6_ISIN\"},"
                + "{\"cusip\": null, \"secSID\": 7, \"issueFactor\": 1, \"commonCode\": \"7_commonCode\", \"blockedBy\": \"7_blockedBy\", \"minorType\": \"7_minorType\", \"shortcode\": \"7_shortcode\", \"sedol\": \"7_sedol\", \"ric\": \"7_ric\", \"descr\": \"7_descr\", \"valueFactor\": 0, \"quickcode\": \"7_quickcode\", \"securityType\": \"7_securityType\", \"maturityDate\": \"2022-03-04\", \"ccy\": \"7_ccy\", \"reservedBy\": \"7_reservedBy\", \"tradedIND\": \"Y\", \"ctryId\": \"7_ctryId\", \"wertpapierCode\": \"7_wertpapierCode\", \"isin\": \"7_ISIN\"}"
                + "]";
        String qualifier = "(<= '2023-01-01' & >= '2020-12-31') | (<= '2028-01-01' & >= '2025-12-31')";

        List<Security> result = filterAndSortSecurities(jsonString, qualifier);
        result.forEach(System.out::println);
    }




}
