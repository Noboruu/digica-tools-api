package com.noboruu.digica.utils;

import com.noboruu.digica.model.dto.CardSetDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardSetNaturalOrderComparator implements Comparator<CardSetDTO> {
    private static final Pattern CARD_SET_REGEX_MATCHER = Pattern.compile("^([a-zA-Z]+)([0-9]+)?$");

    @Override
    public int compare(CardSetDTO o1, CardSetDTO o2) {
        String c1 = o1.getCode();
        String c2 = o2.getCode();

        Matcher m1 = CARD_SET_REGEX_MATCHER.matcher(c1);
        Matcher m2 = CARD_SET_REGEX_MATCHER.matcher(c2);

        if(!(m1.find() && m2.find())) {
            return 0;
        }

        // Compare codes by the set type (BT, EX, ST ...)
        String s1 = m1.group(1);
        String s2 = m2.group(1);

        if(!s1.equals(s2)) {
            return s1.compareToIgnoreCase(s2);
        }

        // Compare codes by set number
        int n1 = StringUtils.isBlank(m1.group(2)) ? 0 : Integer.parseInt(m1.group(2));
        int n2 = StringUtils.isBlank(m2.group(2)) ? 0 : Integer.parseInt(m2.group(2));

        return Integer.compare(n1, n2);
    }
}
